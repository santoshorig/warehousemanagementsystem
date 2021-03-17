package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseInspectionTypesRiskForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseInspectionsRiskForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.exception.CustomException;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * <h1>Warehouse controller</h1>
 * <p>
 * This warehouse controller class provides the CRUD actions for warehouses
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse")
public class WarehouseController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRemarkService warehouseRemarkService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TenderService tenderService;

    @Autowired
    private WarehouseTypeService warehouseTypeService;

    @Autowired
    private TakeoverTypeService takeoverTypeService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private WarehouseInspectionService warehouseInspectionService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.WarehouseService warehouseDomainService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Warehouse index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Warehouse> warehouseList = warehouseService.getByStatusAndBusinessType();
        modelAndView.addObject("models", warehouseList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_WAREHOUSE_STATUS());
        modelAndView.setViewName("admin/warehouse/warehouse/index");
        LOGGER.info("Warehouse index action returned {} models", warehouseList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Warehouse edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Warehouse model = new Warehouse();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Warehouse edit action called on existing model with id {}", identifier);
            Optional<Warehouse> warehouse = warehouseService.get(identifier);
            if (warehouse.isPresent()) {
                model = warehouse.get();
                if (model.getStatus().equalsIgnoreCase(constantService.getWAREHOUSE_STATUS()
                        .get("pendingforbusinessreview")) && !helperService.isBusinessReviewerUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
                if (model.getStatus()
                        .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("underprocess")) && !helperService
                        .isGeneralUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
            }
        } else {
            if (helperService.isGeneralUser()) {
                model.setStatus(constantService.getWAREHOUSE_STATUS().get("underprocess"));
                if (model.getId() == null) {
                    model.setActive(true);
                }
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        List<Employee> employeeList = employeeService.getAll();
        List<RegionLocation> regionLocationList = regionLocationService.getAll();
        List<TakeoverType> takeoverTypeList = takeoverTypeService.getByBusinessType();
        List<WarehouseType> warehouseTypeList = warehouseTypeService.getAll();
        List<Tender> tenderList = tenderService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("employees", employeeList);
        modelAndView.addObject("regionLocations", regionLocationList);
        modelAndView.addObject("takeoverTypes", takeoverTypeList);
        modelAndView.addObject("warehouseTypes", warehouseTypeList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("tenders", tenderList);
        modelAndView.setViewName("admin/warehouse/warehouse/edit");
        LOGGER.info("Warehouse edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Warehouse warehouse,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Warehouse save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse/edit");
        if (!result.hasErrors()) {
            try {
                warehouseService.edit(warehouse);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Warehouse save action successful");
                modelAndView
                        .setViewName("redirect:/admin/warehouse-commodity/index/warehouse/" + warehouse
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Warehouse save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("Warehouse delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseService.delete(id);
            LOGGER.info("Warehouse delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse/index");
        return modelAndView;
    }

    /**
     * Risk approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/risk-approval/{id}"})
    public ModelAndView riskApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Warehouse risk approval action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseInspectionsRiskForm warehouseInspectionsRiskForm = new WarehouseInspectionsRiskForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (helperService.getWarehouseStatusByRole().stream()
                    .anyMatch(warehouseModel.getStatus()::equalsIgnoreCase)) {
                model = warehouseDomainService.get(warehouseModel);
            }
            warehouseInspectionsRiskForm.setWarehouse(warehouseModel);
            Map<InspectionType, List<WarehouseInspection>> inspectionListMap = new HashMap<>();
            List<WarehouseInspection> warehouseInspectionList =
                    warehouseInspectionService.getByWarehouse(warehouseModel);
            for (WarehouseInspection warehouseInspection : warehouseInspectionList) {
                InspectionType inspectionType =
                        warehouseInspection.getInspectionOption().getInspection().getInspectionType();
                if (!inspectionListMap.containsKey(inspectionType)) {
                    inspectionListMap.put(inspectionType, new ArrayList<>());
                }
                inspectionListMap.get(inspectionType).add(warehouseInspection);
            }
            List<WarehouseInspectionTypesRiskForm> warehouseInspectionTypesRiskFormList = new ArrayList<>();
            for (Map.Entry<InspectionType, List<WarehouseInspection>> wi : inspectionListMap.entrySet()) {
                WarehouseInspectionTypesRiskForm warehouseInspectionTypesRiskForm =
                        new WarehouseInspectionTypesRiskForm();
                warehouseInspectionTypesRiskForm.setInspectionType(wi.getKey());
                warehouseInspectionTypesRiskForm.setWarehouseInspections(wi.getValue());
                warehouseInspectionTypesRiskFormList.add(warehouseInspectionTypesRiskForm);
            }
            warehouseInspectionsRiskForm.setWarehouseInspectionTypesRiskForms(warehouseInspectionTypesRiskFormList);
        }

        List<String> statusList = constantService.getRISK_USER_APPROVAL_WAREHOUSE_STATUS();
        Boolean riskApproval = false;
        if (helperService.isRiskHead()) {
            statusList = constantService.getRISK_HEAD_APPROVAL_WAREHOUSE_STATUS();
            riskApproval = true;
        }
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("warehouseRiskForm", warehouseInspectionsRiskForm);
        modelAndView.addObject("riskApproval", riskApproval);
        modelAndView.setViewName("admin/warehouse/warehouse/risk-approval");
        LOGGER.info("Warehouse risk approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Risk approval save action
     *
     * @return the model and view
     */
    @PostMapping("/risk-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseInspectionsRiskForm warehouseInspectionsRiskForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Warehouse risk approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/warehouse/index");
        if (!result.hasErrors()) {
            try {
                Warehouse warehouse = warehouseInspectionsRiskForm.getWarehouse();
                for (WarehouseInspectionTypesRiskForm warehouseInspectionTypesRiskForm : warehouseInspectionsRiskForm
                        .getWarehouseInspectionTypesRiskForms()) {
                    for (WarehouseInspection warehouseInspection : warehouseInspectionTypesRiskForm
                            .getWarehouseInspections()) {
                        Optional<WarehouseInspection> warehouseInspectionModel =
                                warehouseInspectionService.get(warehouseInspection.getId());
                        if (warehouseInspectionModel.isPresent()) {
                            WarehouseInspection wim = warehouseInspectionModel.get();
                            wim.setRiskScore(warehouseInspection.getRiskScore());
                            warehouseInspectionService.edit(wim);
                        }
                    }
                }
                if (!warehouseInspectionsRiskForm.getUpload().isEmpty()) {
                    MultipartFile file = warehouseInspectionsRiskForm.getUpload();
                    Document document = documentService.store(file);
                    warehouse.setTermSheetDocument(document);
                }
                WarehouseRemark warehouseRemark = new WarehouseRemark();
                warehouseRemark.setRemark(warehouseInspectionsRiskForm.getRemark());
                warehouseRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                warehouseRemark.setRemarkDate(new Date());
                warehouseRemark.setWarehouse(warehouse);
                warehouseRemark.setUser(helperService.getLoggedInDbUser());
                warehouseRemarkService.edit(warehouseRemark);
                warehouse.setStatus(warehouseInspectionsRiskForm.getStatus());
                warehouseService.edit(warehouse);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Warehouse risk save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Warehouse risk approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Business approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/business-approval/{id}"})
    public ModelAndView businessApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Warehouse business approval action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseRemarkForm warehouseRemarkForm = new WarehouseRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (helperService.getWarehouseStatusByRole().stream()
                    .anyMatch(warehouseModel.getStatus()::equalsIgnoreCase)) {
                model = warehouseDomainService.get(warehouseModel);
            }
            warehouseRemarkForm.setWarehouse(warehouseModel);
        }

        List<String> statusList = constantService.getBUSINESS_APPROVAL_WAREHOUSE_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("warehouseRemarkForm", warehouseRemarkForm);
        modelAndView.setViewName("admin/warehouse/warehouse/business-approval");
        LOGGER.info("Warehouse business approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Business approval save action
     *
     * @return the model and view
     */
    @PostMapping("/business-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseRemarkForm warehouseRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Warehouse business approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/warehouse/index");
        if (!result.hasErrors()) {
            try {
                Warehouse warehouse = warehouseRemarkForm.getWarehouse();
                warehouse.setStatus(warehouseRemarkForm.getStatus());
                warehouseService.edit(warehouse);
                WarehouseRemark warehouseRemark = new WarehouseRemark();
                warehouseRemark.setRemark(warehouseRemarkForm.getRemark());
                warehouseRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                warehouseRemark.setRemarkDate(new Date());
                warehouseRemark.setWarehouse(warehouse);
                warehouseRemark.setUser(helperService.getLoggedInDbUser());
                warehouseRemarkService.edit(warehouseRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Warehouse business approval save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Warehouse business approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Get warehouse by id action
     *
     * @param id the id of warehouse
     * @return json
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.get(id);
        Warehouse warehouseModel = new Warehouse();
        if (warehouse.isPresent()) {
            warehouseModel = warehouse.get();
        }
        return new ResponseEntity(warehouseModel, HttpStatus.OK);
    }

    /**
     * Get domain warehouse by id action
     *
     * @param id the id of warehouse
     * @return json
     */
    @RequestMapping(value = "/getDomainById/{id}", method = RequestMethod.GET)
    public ResponseEntity getDomainById(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.get(id);
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse warehouseModel =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse();
        if (warehouse.isPresent()) {
            warehouseModel = warehouseDomainService.get(warehouse.get());
        }
        return new ResponseEntity(warehouseModel, HttpStatus.OK);
    }

    /**
     * Download term sheet
     *
     * @param id
     * @return
     */
    @GetMapping("/download-term-sheet/{id}")
    public ResponseEntity<Resource> downloadTermSheet(@PathVariable Integer id) throws FileNotFoundException, CustomException {
        LOGGER.info("Term sheet download action called");
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            try {
                Document document = warehouseDomainService.getTermSheet(warehouse.get());
                LOGGER.info("Term sheet download action returned {} model", document);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(document.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + document.getFileName() + "\"")
                        .body(new ByteArrayResource(document.getFileContent()));
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                throw new CustomException("Invalid term sheet " + e.getLocalizedMessage());
            }
        } else {
            throw new FileNotFoundException("Warehouse not found with id " + id);
        }
    }
}