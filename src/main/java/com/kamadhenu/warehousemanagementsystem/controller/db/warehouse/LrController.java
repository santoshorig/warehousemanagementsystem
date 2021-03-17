package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardSimple;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
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
import java.util.*;

/**
 * <h1>Lr controller</h1>
 * <p>
 * This lr controller class provides the CRUD actions for lrs
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/lr")
public class LrController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LrController.class);

    @Autowired
    private LrService lrService;

    @Autowired
    private LrInwardTruckBagService lrInwardTruckBagService;

    @Autowired
    private LrRemarkService lrRemarkService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.LrService lrDomainService;

    @Autowired
    private SrService srService;

    @Autowired
    private SrInwardTruckQualityCheckService srInwardTruckQualityCheckService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

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
        LOGGER.info("Lr index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<String> srStatusList = constantService.getAPPROVED_SR_STATUS();
        List<Sr> srList = srService.getByStatusAndBusinessType(srStatusList);
        modelAndView.addObject("models", srList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_SR_STATUS());
        modelAndView.setViewName("admin/warehouse/lr/index");
        LOGGER.info("Lr index action returned {} models", srList.size());
        return modelAndView;
    }

    /**
     * Edit action for no sr
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit"})
    public ModelAndView edit() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/lr/index");
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/{sr}"})
    public ModelAndView edit(@PathVariable("sr") Integer sr) {
        LOGGER.info("Lr edit action called");
        ModelAndView modelAndView = new ModelAndView();
        LrForm model = new LrForm();
        LOGGER.info("Lr edit action called on SR with id {}", sr);
        Lr lr = new Lr();
        Optional<Sr> srModel = srService.get(sr);
        if (srModel.isPresent()) {
            Sr existingSrModel = srModel.get();
            // Create map for maintaining uniqueness
            ///TODO Could do with refactoring
            Map<InwardSimple, Map<WarehouseStack, Map<BagType, Map<Integer, Double>>>> inwardMap = new HashMap<>();
            // Get the sr inward truck list
            List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                    srInwardTruckQualityCheckService.getBySr(existingSrModel);
            for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {

                // Get inward trucks against each SR
                InwardTruck inwardTruck = srInwardTruckQualityCheck.getInwardTruck();
                Inward inward = inwardTruck.getInward();
                com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward domainInwardModel =
                        inwardDomainService.get(inward);
                InwardSimple inwardSimple = new InwardSimple();
                inwardSimple.setInward(inward);
                inwardSimple.setTotalBags(domainInwardModel.getTotalBags());
                inwardSimple.setTotalNetWeight(domainInwardModel.getTotalNetWeightMt());
                // Create entry for inward
                if (!inwardMap.containsKey(inwardSimple)) {
                    inwardMap.put(inwardSimple, new HashMap<>());
                }

                // Next get the inward truck bags
                for (com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck domainInwardTruck : domainInwardModel
                        .getInwardTruckList()) {
                    if (domainInwardTruck.getInwardTruck().getId().intValue() == inwardTruck.getId().intValue()) {
                        Map<BagType, Double> averageGrossWeightPerBagTypeMap =
                                domainInwardTruck.getAverageGrossWeightPerBagType();
                        // Find out which bags are liened and group by stack and bag type
                        for (InwardTruckBag inwardTruckBag : domainInwardTruck.getInwardTruckBagList()) {
                            if (inwardTruckBag.getLien()) {
                                Double averageGrossWeightPerBagType = 0.0;
                                if (averageGrossWeightPerBagTypeMap.containsKey(inwardTruckBag.getBagType())) {
                                    averageGrossWeightPerBagType =
                                            averageGrossWeightPerBagTypeMap.get(inwardTruckBag.getBagType());
                                }
                                if (!inwardMap.get(inwardSimple).containsKey(inwardTruckBag.getWarehouseStack())) {
                                    inwardMap.get(inwardSimple)
                                            .put(inwardTruckBag.getWarehouseStack(), new HashMap<>());
                                }
                                if (!inwardMap.get(inwardSimple).get(inwardTruckBag.getWarehouseStack())
                                        .containsKey(inwardTruckBag.getBagType())) {
                                    Map<Integer, Double> bagTypeMap = new HashMap<>();
                                    bagTypeMap.put(1, averageGrossWeightPerBagType);
                                    inwardMap.get(inwardSimple).get(inwardTruckBag.getWarehouseStack())
                                            .put(inwardTruckBag.getBagType(), bagTypeMap);
                                } else {
                                    Map<Integer, Double> bagTypeMap = new HashMap<>();
                                    Integer currentBagCount = 0;
                                    for (Map.Entry<Integer, Double> entry : inwardMap.get(inwardSimple)
                                            .get(inwardTruckBag.getWarehouseStack())
                                            .get(inwardTruckBag.getBagType()).entrySet()) {
                                        currentBagCount = entry.getKey();
                                        currentBagCount++;
                                    }
                                    bagTypeMap.put(currentBagCount, averageGrossWeightPerBagType);
                                    inwardMap.get(inwardSimple).get(inwardTruckBag.getWarehouseStack())
                                            .replace(inwardTruckBag.getBagType(), bagTypeMap);
                                }
                            }
                        }
                    }
                }
            }
            lr.setStatus(constantService.getLR_STATUS().get("underprocess"));
            lr.setLrNumber(helperService.getEpoch().toString());
            model.setLr(lr);
            model.setSr(existingSrModel);

            //Set inward
            List<LrInwardForm> lrInwardFormList = new ArrayList<>();
            for (Map.Entry<InwardSimple, Map<WarehouseStack, Map<BagType, Map<Integer, Double>>>> inwardSimpleMapEntry : inwardMap
                    .entrySet()) {
                InwardSimple inwardSimple = inwardSimpleMapEntry.getKey();
                LrInwardForm lrInwardForm = new LrInwardForm();
                lrInwardForm.setInward(inwardSimple.getInward());
                lrInwardForm.setTotalBags(inwardSimple.getTotalBags());
                lrInwardForm.setTotalNetWeight(inwardSimple.getTotalNetWeight());

                //Set warehouse stacks
                List<LrWarehouseStackForm> lrWarehouseStackFormList = new ArrayList<>();
                for (Map.Entry<WarehouseStack, Map<BagType, Map<Integer, Double>>> warehouseStackMapEntry : inwardSimpleMapEntry
                        .getValue().entrySet()) {
                    WarehouseStack warehouseStack = warehouseStackMapEntry.getKey();
                    LrWarehouseStackForm lrWarehouseStackForm = new LrWarehouseStackForm();
                    lrWarehouseStackForm.setWarehouseStack(warehouseStack);
                    // Add bags
                    List<LrBagTypeForm> lrBagTypeFormList = new ArrayList<>();
                    for (Map.Entry<BagType, Map<Integer, Double>> bagTypeMapEntry : warehouseStackMapEntry.getValue()
                            .entrySet()) {
                        BagType bagType = bagTypeMapEntry.getKey();
                        LrBagTypeForm lrBagTypeForm = new LrBagTypeForm();
                        lrBagTypeForm.setBagType(bagType);
                        for (Map.Entry<Integer, Double> bagsMapEntry : bagTypeMapEntry.getValue().entrySet()) {
                            Integer total = bagsMapEntry.getKey();
                            Double weight = bagsMapEntry.getValue();
                            lrBagTypeForm.setAverageGrossWeight(weight);
                            lrBagTypeForm.setTotal(total);
                            lrBagTypeForm.setNeededWeight(0.0);
                            lrBagTypeForm.setNeededBags(0);
                        }
                        lrBagTypeFormList.add(lrBagTypeForm);
                    }
                    lrWarehouseStackForm.setLrBagTypeFormList(lrBagTypeFormList);
                    lrWarehouseStackFormList.add(lrWarehouseStackForm);
                }
                lrInwardForm.setLrWarehouseStackFormList(lrWarehouseStackFormList);
                lrInwardFormList.add(lrInwardForm);
            }

            model.setLrInwardFormList(lrInwardFormList);
        } else {
            modelAndView.setViewName("redirect:/error/403");
            return modelAndView;
        }

        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        modelAndView.addObject("model", model);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/warehouse/lr/edit");
        LOGGER.info("Lr edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") LrForm lrForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Lr save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/lr/edit");
        if (!result.hasErrors()) {
            try {
                Sr sr = lrForm.getSr();
                Lr lr = lrForm.getLr();
                Lr existingLr = new Lr();
                if (lr.getId() != null) {
                    Optional<Lr> lrModel = lrService.get(lr.getId());
                    if (lrModel.isPresent()) {
                        existingLr = lrModel.get();
                    }
                }
                // save ro document
                if (lrForm.getRoUpload().isEmpty()) {
                    if (existingLr.getId() != null && existingLr.getRoDocument() != null) {
                        lr.setRoDocument(existingLr.getRoDocument());
                    }
                } else {
                    MultipartFile file = lrForm.getRoUpload();
                    Document document = documentService.store(file);
                    lr.setRoDocument(document);
                }
                // save ro email document
                if (lrForm.getRoEmailUpload().isEmpty()) {
                    if (existingLr.getId() != null && existingLr.getRoEmailDocument() != null) {
                        lr.setRoEmailDocument(existingLr.getRoEmailDocument());
                    }
                } else {
                    MultipartFile file = lrForm.getRoEmailUpload();
                    Document document = documentService.store(file);
                    lr.setRoEmailDocument(document);
                }

                lr.setSr(sr);
                sr.setStatus(constantService.getLR_STATUS().get("pendingforbusinessreview"));
                lrService.edit(lr);
                // Add remarks
                LrRemark lrRemark = new LrRemark();
                lrRemark.setRemark(lrForm.getRemark());
                lrRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                lrRemark.setRemarkDate(new Date());
                lrRemark.setLr(lr);
                lrRemark.setUser(helperService.getLoggedInDbUser());
                lrRemarkService.edit(lrRemark);
                // Get the sr inward truck list
                List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                        srInwardTruckQualityCheckService.getBySr(sr);
                for (LrInwardForm lrInwardForm : lrForm.getLrInwardFormList()) {
                    for (LrWarehouseStackForm lrWarehouseStackForm : lrInwardForm.getLrWarehouseStackFormList()) {
                        for (LrBagTypeForm lrBagTypeForm : lrWarehouseStackForm.getLrBagTypeFormList()) {
                            if (lrBagTypeForm.getNeededBags() > 0) {
                                // get total bags needed to remove from lien
                                Integer neededBags = lrBagTypeForm.getNeededBags();
                                ///TODO fix endless save
                                for (Integer i = 1; i <= neededBags; i++) {
                                    // go through each sr inward truck
                                    bagEntered:
                                    for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
                                        InwardTruck inwardTruck = srInwardTruckQualityCheck.getInwardTruck();
                                        Inward inward = inwardTruck.getInward();
                                        // check to make sure inward matches
                                        if (inward.getId() == lrInwardForm.getInward().getId()) {
                                            List<InwardTruckBag> inwardTruckBagList =
                                                    inwardTruckBagService.getByInwardTruck(inwardTruck);
                                            // go through each inward bag
                                            for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
                                                // check to make sure its already liened
                                                if (inwardTruckBag.getLien() && !inwardTruckBag
                                                        .getDoModel() && !inwardTruckBag.getOutward()) {
                                                    // go through each stack
                                                    ///TODO two integers not matchng without equals?
                                                    if (inwardTruckBag.getWarehouseStack()
                                                            .getId().equals(lrWarehouseStackForm
                                                                    .getWarehouseStack().getId())) {
                                                        // go through each bag type
                                                        if (inwardTruckBag.getBagType().getId().equals(lrBagTypeForm
                                                                .getBagType()
                                                                .getId())) {
                                                            // remove lien
                                                            inwardTruckBag.setLien(false);
                                                            inwardTruckBagService.edit(inwardTruckBag);
                                                            LrInwardTruckBag lrInwardTruckBag = new LrInwardTruckBag();
                                                            lrInwardTruckBag.setInwardTruckBag(inwardTruckBag);
                                                            lrInwardTruckBag.setLr(lr);
                                                            lrInwardTruckBagService.edit(lrInwardTruckBag);
                                                            --neededBags;
                                                            break bagEntered;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Lr save action successful");
                modelAndView.setViewName("redirect:/admin/lr/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Lr save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Lr delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            lrService.delete(id);
            LOGGER.info("Lr delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/lr/index");
        return modelAndView;
    }


    /**
     * Review approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/review-approval/{sr}"})
    public ModelAndView reviewApproval(@PathVariable("sr") Integer sr) {
        LOGGER.info("Lr review approval action called");
        ModelAndView modelAndView = new ModelAndView();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Lr lrDomainModel =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Lr();
        LrRemarkStatusForm lrRemarkStatusForm = new LrRemarkStatusForm();
        Optional<Sr> srModel = srService.get(sr);
        ///TODO Fix
        if (srModel.isPresent()) {
            List<Lr> lr = lrService.getBySr(srModel.get());
            if (lr.size() > 0) {
                Lr lrModel = lr.get(0);
                lrRemarkStatusForm.setLr(lrModel);
                lrDomainModel = lrDomainService.get(lrModel);
            }
        }

        List<String> statusList = constantService.getREVIEWER_APPROVAL_LR_STATUS();
        modelAndView.addObject("model", lrRemarkStatusForm);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("lrDomain", lrDomainModel);
        modelAndView.setViewName("admin/warehouse/lr/review-approval");
        LOGGER.info("Lr review approval action returned {} model", lrRemarkStatusForm);
        return modelAndView;
    }

    /**
     * Review approval save action
     *
     * @return the model and view
     */
    @PostMapping("/review-approval-save")
    public ModelAndView reviewApprovalsave(
            @Valid @ModelAttribute("model") LrRemarkStatusForm lrRemarkStatusForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Lr review approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/lr/index");
        if (!result.hasErrors()) {
            try {
                Lr lr = lrRemarkStatusForm.getLr();
                lr.setStatus(lrRemarkStatusForm.getStatus());
                lrService.edit(lr);
                LrRemark lrRemark = new LrRemark();
                lrRemark.setRemark(lrRemarkStatusForm.getRemark());
                lrRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                lrRemark.setRemarkDate(new Date());
                lrRemark.setLr(lr);
                lrRemark.setUser(helperService.getLoggedInDbUser());
                lrRemarkService.edit(lrRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Lr review approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Lr review approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }
}
