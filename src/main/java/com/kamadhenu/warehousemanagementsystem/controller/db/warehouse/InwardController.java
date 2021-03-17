package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.exception.CustomException;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.NotificationService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * <h1>Inward controller</h1>
 * <p>
 * This inward controller class provides the CRUD actions for inwards
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward")
public class InwardController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardController.class);

    @Autowired
    private InwardService inwardService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private InwardRemarkService inwardRemarkService;

    @Autowired
    private InwardMadeUpBagService inwardMadeUpBagService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private WarehouseStackService warehouseStackService;

    @Autowired
    private WarehouseCadService warehouseCadService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService;

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
        LOGGER.info("Inward index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Inward> inwardList = inwardService.getByStatusAndBusinessType();
        modelAndView.addObject("models", inwardList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_INWARD_STATUS());
        modelAndView.setViewName("admin/warehouse/inward/index");
        LOGGER.info("Inward index action returned {} models", inwardList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Inward edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardForm inwardForm = new InwardForm();
        Inward model = new Inward();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Inward edit action called on existing model with id {}", identifier);
            Optional<Inward> inward = inwardService.get(identifier);
            if (inward.isPresent()) {
                model = inward.get();
            }
        } else {
            if (helperService.isInwardGeneralUser()) {
                model.setCadDate(new Date());
                model.setStatus(constantService.getINWARD_STATUS().get("underprocess"));
                model.setUpload(false);
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        inwardForm.setInward(model);

        List<Contract> contractList = new ArrayList<>();
        List<Contract> existingContractList = contractService.getApprovedContract();
        List<Integer> locationList = helperService.getUserLocationsId();
        if (!locationList.isEmpty()) {
            for (Contract contract : existingContractList) {
                Optional<RegionLocation> regionLocation =
                        regionLocationService.get(contract.getWarehouse().getRegLoc());
                if (regionLocation.isPresent()) {
                    RegionLocation regionLocationModel = regionLocation.get();
                    if (locationList.contains(regionLocationModel.getLocation().getId())) {
                        contractList.add(contract);
                    }
                }
            }
        } else {
            contractList = existingContractList;
        }

        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        Map<String, String> inwardTypeMap = constantService.getINWARD_TYPE();
        modelAndView.addObject("model", inwardForm);
        modelAndView.addObject("contracts", contractList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("inwardTypes", inwardTypeMap);
        modelAndView.setViewName("admin/warehouse/inward/edit");
        LOGGER.info("Inward edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardForm inwardForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Inward save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward/edit");
        if (!result.hasErrors()) {
            try {
                Inward inward = inwardForm.getInward();
                WarehouseCad warehouseCad = inward.getWarehouseCad();
                // Double check to make sure cad didn't get used in between editing and submitting
                Optional<WarehouseCad> existingWarehouseCad = warehouseCadService.get(warehouseCad.getId());
                if (existingWarehouseCad.isPresent() && existingWarehouseCad.get().getUsed()) {
                    throw new CustomException("CAD already used");
                }
                Inward existingInward = new Inward();
                if (inward.getId() != null) {
                    Optional<Inward> inwardModel = inwardService.get(inward.getId());
                    if (inwardModel.isPresent()) {
                        existingInward = inwardModel.get();
                    }
                }

                if (inwardForm.getCadDocument().isEmpty()) {
                    if (existingInward.getId() != null && existingInward.getCadDocument() != null) {
                        inward.setCadDocument(existingInward.getCadDocument());
                    }
                } else {
                    MultipartFile file = inwardForm.getCadDocument();
                    Document document = documentService.store(file);
                    inward.setCadDocument(document);
                }

                if (inwardForm.getCddDocument().isEmpty()) {
                    if (existingInward.getId() != null && existingInward.getCddDocument() != null) {
                        inward.setCddDocument(existingInward.getCddDocument());
                    }
                } else {
                    MultipartFile file = inwardForm.getCddDocument();
                    Document document = documentService.store(file);
                    inward.setCddDocument(document);
                }

                if (inwardForm.getGrnDocument().isEmpty()) {
                    if (existingInward.getId() != null && existingInward.getGrnDocument() != null) {
                        inward.setGrnDocument(existingInward.getGrnDocument());
                    }
                } else {
                    MultipartFile file = inwardForm.getGrnDocument();
                    Document document = documentService.store(file);
                    inward.setGrnDocument(document);
                }

                inwardService.edit(inward);
                Integer daysBehindFromNow =
                        helperService.getDaysBehindFromNow(inward.getCadDate());
                if (daysBehindFromNow > constantService.getINWARD_OLD()) {
                    Notification notification = new Notification();
                    notification.setNotification(constantService.getNOTIFICATION_INWARD_OLD() + inwardForm
                            .getInward().getWarehouseCad()
                            .getFriendlyName());
                    notification.setNotificationDate(new Date());
                    notification.setStatus(constantService.getNOTIFICATION_STATUS().get("unread"));
                    notification.setRole(constantService.getNOTIFICATION_INWARD_OLD_ROLE());
                    notificationService.edit(notification);
                }

                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Inward save action successful");
                if (inward.getInwardType().equalsIgnoreCase(constantService.getINWARD_TYPE().get("Made-Up Bag"))) {
                    modelAndView.setViewName("redirect:/admin/inward-made-up-bag/index/inward/" + inward
                            .getId());
                } else {
                    modelAndView.setViewName("redirect:/admin/inward-truck/index/inward/" + inward
                            .getId());
                }
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Inward save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Inward delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            inwardService.delete(id);
            LOGGER.info("Inward delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward/index");
        return modelAndView;
    }

    /**
     * Approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/approve/{id}"})
    public ModelAndView approve(@PathVariable("id") Integer id) {
        LOGGER.info("Inward approve action called with id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Optional<Inward> inward = inwardService.get(id);
        if (inward.isPresent()) {
            try {
                Inward inwardModel = inward.get();
                WarehouseCad warehouseCad = inwardModel.getWarehouseCad();
                // Double check to make sure cad didn't get used in between editing and submitting
                Optional<WarehouseCad> existingWarehouseCad = warehouseCadService.get(warehouseCad.getId());
                if (existingWarehouseCad.isPresent() && existingWarehouseCad.get().getUsed()) {
                    throw new CustomException("CAD already used");
                }
                if (helperService.isInwardGeneralUser()) {
                    inwardModel.setStatus(constantService.getINWARD_STATUS().get("pendingforreview"));
                    inwardService.edit(inwardModel);
                    modelAndView.setViewName("redirect:/admin/inward/index");
                } else {
                    modelAndView.setViewName("redirect:/error/403");
                }
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("redirect:/error/403");
        }
        return modelAndView;
    }

    /**
     * Reviewer approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/review-approval/{id}"})
    public ModelAndView reviewApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Inward review approval action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardRemarkForm inwardRemarkForm = new InwardRemarkForm();
        Map<Integer, Double> warehouseMadeUpBagStackUsagePercentage = new HashMap<>();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward();
        Optional<Inward> inward = inwardService.get(id);
        if (inward.isPresent()) {
            try {
                Inward inwardModel = inward.get();
                WarehouseCad warehouseCad = inwardModel.getWarehouseCad();
                // Double check to make sure cad didn't get used in between editing and submitting
                Optional<WarehouseCad> existingWarehouseCad = warehouseCadService.get(warehouseCad.getId());
                if (existingWarehouseCad.isPresent() && existingWarehouseCad.get().getUsed()) {
                    inwardModel.setStatus(constantService.getINWARD_STATUS().get("underprocess"));
                    inwardService.edit(inwardModel);
                    throw new CustomException("CAD already used");
                }
                List<WarehouseStack> warehouseStackList = new ArrayList<>();
                if (helperService.getInwardStatusByRole().stream()
                        .anyMatch(inwardModel.getStatus()::equalsIgnoreCase)) {
                    model = inwardDomainService.get(inwardModel);
                    Map<Integer, WarehouseStack> warehouseStackMap = new HashMap<>();
                    for (InwardTruck inwardTruck : model.getInwardTruckList()) {
                        for (InwardTruckBag inwardTruckBag : inwardTruck.getInwardTruckBagList()) {
                            WarehouseStack warehouseStack = inwardTruckBag.getWarehouseStack();
                            if (!warehouseStackMap.containsKey(warehouseStack.getId())) {
                                warehouseStackMap.put(warehouseStack.getId(), warehouseStack);
                            }
                        }
                    }
                    for (Map.Entry<Integer, WarehouseStack> warehouseStackEntry : warehouseStackMap.entrySet()) {
                        warehouseStackList.add(warehouseStackEntry.getValue());
                    }
                }
                inwardRemarkForm.setInward(inwardModel);
                inwardRemarkForm.setWarehouseStackList(warehouseStackList);

                Warehouse warehouse = inwardModel.getContract().getWarehouse();
                List<WarehouseStack> warehouseNonFullStackList = warehouseStackService.getNonFullByWarehouse(warehouse);
                warehouseStackList = warehouseStackService.getByWarehouse(warehouse);
                for (WarehouseStack warehouseStack : warehouseStackList) {
                    if (warehouseNonFullStackList.contains(warehouseStack)) {
                        List<InwardTruckBag> inwardTruckBagByWarehouseStack =
                                inwardTruckBagService.getByWarehouseStack(warehouseStack);
                        Integer bagCount = inwardTruckBagByWarehouseStack.size();
                        // Made up bags
                        List<InwardMadeUpBag> inwardMadeUpBagList =
                                inwardMadeUpBagService.getByWarehouseStack(warehouseStack);
                        Integer madeUpBagCount = inwardMadeUpBagList.size();
                        Double madeUpBagPercentage = 0.0;
                        if (bagCount > 0 && madeUpBagCount > 0) {
                            madeUpBagPercentage = new BigDecimal(
                                    (Double.valueOf(madeUpBagCount) / Double.valueOf(bagCount.floatValue())) * 100)
                                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
                        }
                        warehouseMadeUpBagStackUsagePercentage.put(warehouseStack.getId(), madeUpBagPercentage);
                    }
                }

            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                return modelAndView;
            }
        }

        List<String> statusList = constantService.getREVIEWER_APPROVAL_INWARD_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("inwardRemarkForm", inwardRemarkForm);
        modelAndView.addObject("warehouseMadeUpBagStackUsagePercentage", warehouseMadeUpBagStackUsagePercentage);
        modelAndView.setViewName("admin/warehouse/inward/review-approval");
        LOGGER.info("Inward review approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Review approval save action
     *
     * @return the model and view
     */
    @PostMapping("/review-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardRemarkForm inwardRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Inward review approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/inward/index");
        if (!result.hasErrors()) {
            try {
                Inward inward = inwardRemarkForm.getInward();
                WarehouseCad warehouseCad = inward.getWarehouseCad();
                // Double check to make sure cad didn't get used in between editing and submitting
                Optional<WarehouseCad> existingWarehouseCad = warehouseCadService.get(warehouseCad.getId());
                if (existingWarehouseCad.isPresent() && existingWarehouseCad.get().getUsed()) {
                    inward.setStatus(constantService.getINWARD_STATUS().get("underprocess"));
                    inwardService.edit(inward);
                    throw new CustomException("CAD already used");
                }
                inward.setStatus(inwardRemarkForm.getStatus());
                inwardService.edit(inward);
                // Set cad to be used
                if (inwardRemarkForm.getStatus().equalsIgnoreCase(constantService.getINWARD_STATUS().get("approved"))) {
                    warehouseCad.setUsed(true);
                    warehouseCadService.edit(warehouseCad);
                }

                if (inwardRemarkForm.getWarehouseStackList() != null) {
                    for (WarehouseStack warehouseStack : inwardRemarkForm.getWarehouseStackList()) {
                        warehouseStackService.edit(warehouseStack);
                    }
                }
                InwardRemark inwardRemark = new InwardRemark();
                inwardRemark.setRemark(inwardRemarkForm.getRemark());
                inwardRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                inwardRemark.setRemarkDate(new Date());
                inwardRemark.setInward(inward);
                inwardRemark.setUser(helperService.getLoggedInDbUser());
                inwardRemarkService.edit(inwardRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Warehouse business approval save action successful");
                modelAndView.setViewName("redirect:/admin/inward/index");
            } catch (CustomException e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                return modelAndView;
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Inward review approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Check if inward cdd number exists
     *
     * @param cdd
     * @param contract
     * @return
     */
    @RequestMapping(value = {"/check-cdd/{cdd}/{contract}"}, method = RequestMethod.GET)
    public ResponseEntity checkCode(@PathVariable String cdd, @PathVariable Integer contract) {
        Boolean valid = true;
        List<Inward> inwardList = inwardService.getByCddNumber(cdd);
        if (!inwardList.isEmpty()) {
            for (Inward inwardModel : inwardList) {
                if (inwardModel.getContract().getId().intValue() != contract) {
                    valid = false;
                    break;
                }
            }
        }
        return new ResponseEntity(valid, HttpStatus.OK);
    }
}
