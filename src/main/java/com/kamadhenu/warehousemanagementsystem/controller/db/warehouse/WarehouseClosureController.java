package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseClosureForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseClosureRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosureRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseClosureRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseClosureService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>WarehouseClosure controller</h1>
 * <p>
 * This warehouse closure controller class provides the CRUD actions for warehouse closures
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-closure")
public class WarehouseClosureController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseClosureController.class);

    @Autowired
    private WarehouseClosureService warehouseClosureService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private WarehouseClosureRemarkService warehouseClosureRemarkService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.WarehouseService warehouseDomainService;

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
        LOGGER.info("WarehouseClosure index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseClosure> warehouseClosureList = warehouseClosureService.getByStatus();
        modelAndView.addObject("models", warehouseClosureList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_WAREHOUSE_CLOSURE_STATUS());
        modelAndView.setViewName("admin/warehouse/warehouse-closure/index");
        LOGGER.info("WarehouseClosure index action returned {} models", warehouseClosureList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{warehouse}", "/edit/{warehouse}/{id}"})
    public ModelAndView edit(
            @PathVariable("warehouse") Optional<Integer> warehouse,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("WarehouseClosure edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseClosureForm warehouseClosureForm = new WarehouseClosureForm();
        WarehouseClosure model = new WarehouseClosure();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("WarehouseClosure edit action called on existing model with id {}", identifier);
            Optional<WarehouseClosure> warehouseClosure = warehouseClosureService.get(identifier);
            if (warehouseClosure.isPresent()) {
                model = warehouseClosure.get();
                if (model.getStatus().equalsIgnoreCase(constantService.getWAREHOUSE_CLOSURE_STATUS()
                        .get("pendingforbusinessreview")) && !helperService.isBusinessReviewerUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
                if (model.getStatus()
                        .equalsIgnoreCase(constantService.getWAREHOUSE_CLOSURE_STATUS()
                                .get("underprocess")) && !helperService
                        .isGeneralUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
            }
        } else {
            if (helperService.isGeneralUser()) {
                model.setStatus(constantService.getWAREHOUSE_CLOSURE_STATUS().get("underprocess"));
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }
        warehouseClosureForm.setWarehouseClosure(model);

        List<Inward> inwardList = inwardService.getOpen();
        List<Warehouse> disallowedWarehouseList = new ArrayList<>();
        for (Inward inward : inwardList) {
            disallowedWarehouseList.add(inward.getWarehouseCad().getWarehouse());
        }
        List<Warehouse> warehouseList = warehouseService.getAll();
        List<Warehouse> allowedWarehouseList = new ArrayList<>();
        for (Warehouse warehouseModel : warehouseList) {
            if (!disallowedWarehouseList.contains(warehouseModel)) {
                if (!allowedWarehouseList.contains(warehouseModel)) {
                    allowedWarehouseList.add(warehouseModel);
                }
            }
        }

        if (warehouse.isPresent()) {
            Integer identifier = warehouse.map(Integer::intValue).get();
            Optional<Warehouse> warehouseModel = warehouseService.get(identifier);
            if (warehouseModel.isPresent()) {
                Warehouse editWarehouseModel = warehouseModel.get();
                if (allowedWarehouseList.contains(editWarehouseModel)) {
                    allowedWarehouseList.remove(editWarehouseModel);
                    allowedWarehouseList.add(0, editWarehouseModel);
                    com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse warehouseDomainModel =
                            warehouseDomainService.get(editWarehouseModel);
                    modelAndView.addObject("warehouse", warehouseDomainModel);
                    warehouseClosureForm.getWarehouseClosure().setWarehouse(editWarehouseModel);
                } else {
                    modelAndView.addObject("error", "Warehouse is not available in the allowedWarehouseList");
                    modelAndView.setViewName("error/500");
                    return modelAndView;
                }
            }
        } else {
            // Need to ensure edit is always called with warehouse id
            if (allowedWarehouseList.size() > 0) {
                modelAndView
                        .setViewName("redirect:/admin/warehouse-closure/edit/" + allowedWarehouseList.get(0).getId());
                return modelAndView;
            } else {
                modelAndView.addObject("error", "Allowed Warehouse List is null or empty..");
                modelAndView.setViewName("error/500");
                return modelAndView;
            }
        }

        modelAndView.addObject("model", warehouseClosureForm);
        modelAndView.addObject("warehouses", allowedWarehouseList);
        modelAndView.setViewName("admin/warehouse/warehouse-closure/edit");
        LOGGER.info("WarehouseClosure edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseClosureForm warehouseClosureForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseClosure save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-closure/edit");
        if (!result.hasErrors()) {
            try {
                WarehouseClosure warehouseClosure = warehouseClosureForm.getWarehouseClosure();
                WarehouseClosure existingWarehouseClosure = new WarehouseClosure();
                if (warehouseClosure.getId() != null) {
                    Optional<WarehouseClosure> warehouseClosureModel =
                            warehouseClosureService.get(warehouseClosure.getId());
                    if (warehouseClosureModel.isPresent()) {
                        existingWarehouseClosure = warehouseClosureModel.get();
                    }
                }
                if (warehouseClosureForm.getDehireLetter().isEmpty()) {
                    if (existingWarehouseClosure.getId() != null && existingWarehouseClosure
                            .getDehireLetter() != null) {
                        warehouseClosure.setDehireLetter(existingWarehouseClosure.getDehireLetter());
                    }
                } else {
                    MultipartFile file = warehouseClosureForm.getDehireLetter();
                    Document document = documentService.store(file);
                    warehouseClosure.setDehireLetter(document);
                }
                if (warehouseClosureForm.getReview()) {
                    if (warehouseClosure.getStatus()
                            .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessreview"))) {
                        warehouseClosure
                                .setStatus(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessapproval"));
                    } else {
                        warehouseClosure
                                .setStatus(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessreview"));
                    }
                } else {
                    warehouseClosure.setStatus(constantService.getWAREHOUSE_STATUS().get("rework"));
                }
                warehouseClosureService.edit(warehouseClosure);
                WarehouseClosureRemark warehouseClosureRemark = new WarehouseClosureRemark();
                warehouseClosureRemark.setRemark(warehouseClosureForm.getRemark());
                warehouseClosureRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                warehouseClosureRemark.setRemarkDate(new Date());
                warehouseClosureRemark.setWarehouseClosure(warehouseClosure);
                warehouseClosureRemark.setUser(helperService.getLoggedInDbUser());
                warehouseClosureRemarkService.edit(warehouseClosureRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseClosure save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-closure/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseClosure save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseClosure delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseClosureService.delete(id);
            LOGGER.info("WarehouseClosure delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-closure/index");
        return modelAndView;
    }

    /**
     * Business approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/business-approval/{id}"})
    public ModelAndView businessApproval(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseClosure business approval action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseClosureRemarkForm warehouseClosureRemarkForm = new WarehouseClosureRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse();
        Optional<WarehouseClosure> warehouseClosure = warehouseClosureService.get(id);
        if (warehouseClosure.isPresent()) {
            WarehouseClosure warehouseClosureModel = warehouseClosure.get();
            if (helperService.getWarehouseClosureStatusByRole().stream()
                    .anyMatch(warehouseClosureModel.getStatus()::equalsIgnoreCase)) {
                model = warehouseDomainService.get(warehouseClosureModel.getWarehouse());
            }
            warehouseClosureRemarkForm.setWarehouseClosure(warehouseClosureModel);
        }

        List<String> statusList = constantService.getBUSINESS_APPROVAL_WAREHOUSE_CLOSURE_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("warehouseClosureRemarkForm", warehouseClosureRemarkForm);
        modelAndView.setViewName("admin/warehouse/warehouse-closure/business-approval");
        LOGGER.info("WarehouseClosure business approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Business approval save action
     *
     * @return the model and view
     */
    @PostMapping("/business-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseClosureRemarkForm warehouseClosureRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseClosure business approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/warehouse-closure/index");
        if (!result.hasErrors()) {
            try {
                WarehouseClosure warehouseClosure = warehouseClosureRemarkForm.getWarehouseClosure();
                warehouseClosure.setStatus(warehouseClosureRemarkForm.getStatus());
                warehouseClosureService.edit(warehouseClosure);
                WarehouseClosureRemark warehouseClosureRemark = new WarehouseClosureRemark();
                warehouseClosureRemark.setRemark(warehouseClosureRemarkForm.getRemark());
                warehouseClosureRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                warehouseClosureRemark.setRemarkDate(new Date());
                warehouseClosureRemark.setWarehouseClosure(warehouseClosure);
                warehouseClosureRemark.setUser(helperService.getLoggedInDbUser());
                warehouseClosureRemarkService.edit(warehouseClosureRemark);
                Warehouse warehouse = warehouseClosure.getWarehouse();
                warehouse.setStatus(constantService.getWAREHOUSE_STATUS().get("inactive"));
                warehouseService.edit(warehouse);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseClosure business approval save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-closure/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false, result.getAllErrors().toString());
            LOGGER.error("WarehouseClosure business approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }
}