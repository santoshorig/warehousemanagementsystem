package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoBagTypeForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoInwardForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoRemarkForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoWarehouseStackForm;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardSimple;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

/**
 * <h1>DoInwardTruckBag controller</h1>
 * <p>
 * This do inward truck bag controller class provides the CRUD actions for do inward truck bags
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/do-inward-truck-bag")
public class DoInwardTruckBagController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoInwardTruckBagController.class);

    @Autowired
    private DoInwardTruckBagService doInwardTruckBagService;

    @Autowired
    private DoService doService;

    @Autowired
    private DoRemarkService doRemarkService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private ConstantService constantService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/do/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("DoInwardTruckBag index action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/do-inward-truck-bag/edit/do/" + id);
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("DoInwardTruckBag edit action called");
        ModelAndView modelAndView = new ModelAndView();
        DoRemarkForm model = new DoRemarkForm();
        Optional<Do> doModel = doService.get(id);
        if (doModel.isPresent()) {
            Do existingDoModel = doModel.get();
            // Create map for maintaining uniqueness
            ///TODO Could do with refactoring
            Map<InwardSimple, Map<WarehouseStack, Map<BagType, Map<Integer, Double>>>> inwardMap = new HashMap<>();
            List<Inward> inwardList = inwardService.getApprovedByContract(existingDoModel.getContract());
            for (Inward inward : inwardList) {
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
                    Map<BagType, Double> averageGrossWeightPerBagTypeMap =
                            domainInwardTruck.getAverageGrossWeightPerBagType();
                    // Find out which bags are not liened and group by stack and bag type
                    for (InwardTruckBag inwardTruckBag : domainInwardTruck.getInwardTruckBagList()) {
                        if (!inwardTruckBag.getLien() && !inwardTruckBag.getDoModel() && !inwardTruckBag.getOutward()) {
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
            existingDoModel.setStatus(constantService.getDO_STATUS().get("underprocess"));
            model.setDoModel(existingDoModel);

            //Set inward
            List<DoInwardForm> doInwardFormList = new ArrayList<>();
            for (Map.Entry<InwardSimple, Map<WarehouseStack, Map<BagType, Map<Integer, Double>>>> inwardSimpleMapEntry : inwardMap
                    .entrySet()) {
                if (inwardSimpleMapEntry.getValue().size() > 0) {
                    InwardSimple inwardSimple = inwardSimpleMapEntry.getKey();
                    DoInwardForm doInwardForm = new DoInwardForm();
                    doInwardForm.setInward(inwardSimple.getInward());
                    doInwardForm.setTotalBags(inwardSimple.getTotalBags());
                    doInwardForm.setTotalNetWeight(inwardSimple.getTotalNetWeight());

                    //Set warehouse stacks
                    List<DoWarehouseStackForm> doWarehouseStackFormList = new ArrayList<>();
                    for (Map.Entry<WarehouseStack, Map<BagType, Map<Integer, Double>>> warehouseStackMapEntry : inwardSimpleMapEntry
                            .getValue().entrySet()) {
                        WarehouseStack warehouseStack = warehouseStackMapEntry.getKey();
                        DoWarehouseStackForm doWarehouseStackForm = new DoWarehouseStackForm();
                        doWarehouseStackForm.setWarehouseStack(warehouseStack);
                        // Add bags
                        List<DoBagTypeForm> doBagTypeFormList = new ArrayList<>();
                        for (Map.Entry<BagType, Map<Integer, Double>> bagTypeMapEntry : warehouseStackMapEntry
                                .getValue()
                                .entrySet()) {
                            BagType bagType = bagTypeMapEntry.getKey();
                            DoBagTypeForm doBagTypeForm = new DoBagTypeForm();
                            doBagTypeForm.setBagType(bagType);
                            for (Map.Entry<Integer, Double> bagsMapEntry : bagTypeMapEntry.getValue().entrySet()) {
                                Integer total = bagsMapEntry.getKey();
                                Double weight = bagsMapEntry.getValue();
                                doBagTypeForm.setAverageGrossWeight(weight);
                                doBagTypeForm.setTotal(total);
                                doBagTypeForm.setNeededWeight(0.0);
                                doBagTypeForm.setNeededBags(0);
                            }
                            doBagTypeFormList.add(doBagTypeForm);
                        }
                        doWarehouseStackForm.setDoBagTypeFormList(doBagTypeFormList);
                        doWarehouseStackFormList.add(doWarehouseStackForm);
                    }
                    doInwardForm.setDoWarehouseStackFormList(doWarehouseStackFormList);
                    doInwardFormList.add(doInwardForm);
                }
            }

            model.setDoInwardFormList(doInwardFormList);
        } else {
            modelAndView.setViewName("redirect:/error/403");
            return modelAndView;
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/do-inward-truck-bag/edit");
        LOGGER.info("DoInwardTruckBag edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") DoRemarkForm doRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("DoInwardTruckBag save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/do-inward-truck-bag/edit");
        if (!result.hasErrors()) {
            try {
                Do doModel = doRemarkForm.getDoModel();
                doModel.setStatus(constantService.getDO_STATUS().get("pendingforbusinessreview"));
                doService.edit(doModel);
                // Add remarks
                DoRemark doRemark = new DoRemark();
                doRemark.setRemark(doRemarkForm.getRemark());
                doRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                doRemark.setRemarkDate(new Date());
                doRemark.setDoModel(doModel);
                doRemark.setUser(helperService.getLoggedInDbUser());
                doRemarkService.edit(doRemark);
                for (DoInwardForm doInwardForm : doRemarkForm.getDoInwardFormList()) {
                    List<InwardTruck> inwardTruckList = inwardTruckService.getByInward(doInwardForm.getInward());
                    for (DoWarehouseStackForm doWarehouseStackForm : doInwardForm.getDoWarehouseStackFormList()) {
                        for (DoBagTypeForm doBagTypeForm : doWarehouseStackForm.getDoBagTypeFormList()) {
                            if (doBagTypeForm.getNeededBags() > 0) {
                                // get total bags needed to add to DO
                                Integer neededBags = doBagTypeForm.getNeededBags();
                                Double weight = doBagTypeForm.getAverageGrossWeight();
                                if (neededBags > 0) {
                                    for (InwardTruck inwardTruck : inwardTruckList) {
                                        List<InwardTruckBag> inwardTruckBagList = inwardTruckBagService
                                                .getAvailableForDoByInwardTruckAndWarehouseStackAndBagType(
                                                        inwardTruck,
                                                        doWarehouseStackForm.getWarehouseStack(),
                                                        doBagTypeForm.getBagType()
                                                );
                                        Iterator<InwardTruckBag> iterator = inwardTruckBagList.iterator();
                                        while (neededBags > 0 && iterator.hasNext()) {
                                            InwardTruckBag inwardTruckBag = iterator.next();
                                            inwardTruckBag.setDoModel(true);
                                            inwardTruckBagService.edit(inwardTruckBag);
                                            DoInwardTruckBag doInwardTruckBag = new DoInwardTruckBag();
                                            doInwardTruckBag.setCalculatedWeight(weight);
                                            doInwardTruckBag.setOutward(false);
                                            doInwardTruckBag.setInwardTruckBag(inwardTruckBag);
                                            doInwardTruckBag.setDoModel(doModel);
                                            doInwardTruckBagService.edit(doInwardTruckBag);
                                            // Remove from list once added
                                            iterator.remove();
                                            --neededBags;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("DoInwardTruckBag save action successful");
                modelAndView.setViewName("redirect:/admin/do/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("DoInwardTruckBag save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }
}
