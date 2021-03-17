package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardTruckBagForm;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BagTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardMadeUpBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseStackService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <h1>InwardTruckBag controller</h1>
 * <p>
 * This inward truck bag controller class provides the CRUD actions for inward truck bags
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-truck-bag")
public class InwardTruckBagController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardTruckBagController.class);

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private InwardMadeUpBagService inwardMadeUpBagService;

    @Autowired
    private WarehouseStackService warehouseStackService;

    @Autowired
    private BagTypeService bagTypeService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/inwardTruck/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("InwardTruckBag index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InwardTruckBag> inwardTruckBagList = new ArrayList<>();
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(id);
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            inwardTruckBagList = inwardTruckBagService.getByInwardTruck(inwardTruckModel);
            // Calculate how many more bags/bales/drums can be allowed
            Integer allowedBagsBalesDrums = 0;
            Integer totalBagsBalesDrumsAllowed = inwardTruckModel.getTotalBagsBalesDrums();
            if (inwardTruckBagList.size() > 0) {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed - inwardTruckBagList.size();
            } else {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed;
            }
            modelAndView.addObject("allowedBagsBalesDrums", allowedBagsBalesDrums);
            modelAndView.addObject("inwardTruck", inwardTruckModel);
        }
        if (inwardTruckBagList.size() > 0) {
            modelAndView.addObject("models", inwardTruckBagList);
            modelAndView.setViewName("admin/warehouse/inward-truck-bag/index");
        } else {
            modelAndView.setViewName("redirect:/admin/inward-truck-bag/edit/inwardTruck/" + id);
        }
        LOGGER.info("InwardTruckBag index action returned {} models", inwardTruckBagList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/inwardTruck/{inwardTruckId}", "/edit/inwardTruck/{inwardTruckId}/{id}"})
    public ModelAndView edit(
            @PathVariable("inwardTruckId") Integer inwardTruckId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("InwardTruckBag edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardTruckBagForm model = new InwardTruckBagForm();
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(inwardTruckId);
        List<WarehouseStack> warehouseNonFullStackList = new ArrayList<>();
        List<WarehouseStack> warehouseStackList = new ArrayList<>();
        Map<Integer, Double> warehouseStackUsageWeight = new HashMap<>();
        Map<Integer, Integer> warehouseStackUsageCount = new HashMap<>();
        Map<Integer, Integer> warehouseMadeUpBagStackUsageCount = new HashMap<>();
        Boolean needsManualWeighment = false;
        Integer allowedBagsBalesDrums = 0;
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            InwardTruckBag inwardTruckBagModel = new InwardTruckBag();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("InwardTruck edit action called on existing model with id {}", identifier);
                Optional<InwardTruckBag> inwardTruckBag = inwardTruckBagService.get(identifier);
                if (inwardTruckBag.isPresent()) {
                    inwardTruckBagModel = inwardTruckBag.get();
                    model.setTotalBags(1); // as only 1 bag if id is present
                }
            }

            inwardTruckBagModel.setCalculatedWeight(0.0); // set 0 by default
            inwardTruckBagModel.setLien(false); // set liened to no
            inwardTruckBagModel.setDoModel(false); // set do to no
            inwardTruckBagModel.setOutward(false); // set outward to no
            inwardTruckBagModel.setInwardTruck(inwardTruckModel);
            model.setInwardTruckBag(inwardTruckBagModel);
            if (inwardTruckModel.getWeighbridge() == null) {
                needsManualWeighment = true;
            }
            Warehouse warehouse = inwardTruckModel.getInward().getContract().getWarehouse();
            warehouseNonFullStackList = warehouseStackService.getNonFullByWarehouse(warehouse);
            warehouseStackList = warehouseStackService.getByWarehouse(warehouse);
            for (WarehouseStack warehouseStack : warehouseStackList) {
                if (warehouseNonFullStackList.contains(warehouseStack)) {
                    Integer bagCount = 0;
                    Double weight = 0.0;
                    List<InwardTruckBag> inwardTruckBagByWarehouseStack =
                            inwardTruckBagService.getByWarehouseStack(warehouseStack);
                    for (InwardTruckBag inwardTruckBag : inwardTruckBagByWarehouseStack) {
                        bagCount++;
                        weight += inwardTruckBag.getManualWeight() != null ? inwardTruckBag
                                .getManualWeight() : inwardTruckBag.getCalculatedWeight();

                    }
                    warehouseStackUsageCount.put(warehouseStack.getId(), bagCount);
                    warehouseStackUsageWeight.put(warehouseStack.getId(), weight);

                    // Made up bags
                    Integer madeUpBagCount = 0;
                    List<InwardMadeUpBag> inwardMadeUpBagList =
                            inwardMadeUpBagService.getByWarehouseStack(warehouseStack);
                    madeUpBagCount += inwardMadeUpBagList.size();
                    warehouseMadeUpBagStackUsageCount.put(warehouseStack.getId(), madeUpBagCount);
                }
            }

            // Calculate how many more bags/bales/drums can be allowed
            Integer totalBagsBalesDrumsAllowed = inwardTruckModel.getTotalBagsBalesDrums();
            List<InwardTruckBag> inwardTruckBagList = inwardTruckBagService.getByInwardTruck(inwardTruckModel);
            if (inwardTruckBagList.size() > 0) {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed - inwardTruckBagList.size();
                if (allowedBagsBalesDrums <= 0) {
                    modelAndView.setViewName("redirect:/admin/inward-truck-bag/index/inwardTruck/" + inwardTruckModel
                            .getId());
                    return modelAndView;
                }
            } else {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed;
            }
        }

        List<BagType> bagTypeList = bagTypeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("allowedBagsBalesDrums", allowedBagsBalesDrums);
        modelAndView.addObject("warehouseStacks", warehouseNonFullStackList);
        modelAndView.addObject("bagTypes", bagTypeList);
        modelAndView.addObject("needsManualWeighment", needsManualWeighment);
        modelAndView.addObject("warehouseStackUsageCount", warehouseStackUsageCount);
        modelAndView.addObject("warehouseStackUsageWeight", warehouseStackUsageWeight);
        modelAndView.addObject("warehouseMadeUpBagStackUsageCount", warehouseMadeUpBagStackUsageCount);
        modelAndView.setViewName("admin/warehouse/inward-truck-bag/edit");
        LOGGER.info("InwardTruckBag edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardTruckBagForm inwardTruckBagForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardTruckBag save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward-truck-bag/edit");
        if (!result.hasErrors()) {
            try {
                Double bagsForExternalQc =
                        Math.ceil(inwardTruckBagForm
                                .getTotalBags() / constantService.getEXTERNAL_QC_PERCENTAGE());
                List<Integer> bagsRangeList = IntStream.rangeClosed(1, inwardTruckBagForm.getTotalBags())
                        .boxed().collect(Collectors.toList());
                Collections.shuffle(bagsRangeList);
                List<Integer> bagsRangeSubList = bagsRangeList.subList(0, bagsForExternalQc.intValue());
                com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward inward =
                        new com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward();
                List<InwardTruckBag> inwardTruckBagList = new ArrayList<InwardTruckBag>();
                for (Integer i = 1; i <= inwardTruckBagForm.getTotalBags(); i++) {
                    InwardTruckBag inwardTruckBag = new InwardTruckBag();
                    inwardTruckBag.setCalculatedWeight(inwardTruckBagForm.getInwardTruckBag().getCalculatedWeight());
                    inwardTruckBag.setManualWeight(inwardTruckBagForm.getInwardTruckBag().getManualWeight());
                    inwardTruckBag.setLien(inwardTruckBagForm.getInwardTruckBag().getLien());
                    inwardTruckBag.setDoModel(inwardTruckBagForm.getInwardTruckBag().getDoModel());
                    inwardTruckBag.setOutward(inwardTruckBagForm.getInwardTruckBag().getOutward());
                    inwardTruckBag.setBagType(inwardTruckBagForm.getInwardTruckBag().getBagType());
                    inwardTruckBag.setWarehouseStack(inwardTruckBagForm.getInwardTruckBag().getWarehouseStack());
                    inwardTruckBag.setInwardTruck(inwardTruckBagForm.getInwardTruckBag().getInwardTruck());
                    inwardTruckBag.setExternalQc(bagsRangeSubList.contains(i));
                    inward = inwardTruckBag.getInwardTruck().getInward();
                    inwardTruckBagList.add(inwardTruckBag);
                }
                
                inwardTruckBagService.editBulk(inwardTruckBagList);

                // Now get the domain inward truck data to calculate the average bag weights
                if (inward.getId() != null) {
                    Boolean averageWeightsUpdated = inwardService.updateAverageWeights(inward);
                } else {
                    throw new Exception("Unable to find inward for this inward truck");
                }

                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InwardTruckBag save action successful");
                modelAndView.setViewName("redirect:/admin/inward-truck-bag/index/inwardTruck/" + inwardTruckBagForm
                        .getInwardTruckBag()
                        .getInwardTruck()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InwardTruckBag save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InwardTruckBag delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer inwardTruckId = null;
        try {
            Optional<InwardTruckBag> inwardTruckBag = inwardTruckBagService.get(id);
            if (inwardTruckBag.isPresent()) {
                InwardTruckBag model = inwardTruckBag.get();
                inwardTruckId = model.getInwardTruck().getId();
                inwardTruckBagService.delete(id);
                LOGGER.info("InwardTruckBag delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward-truck-bag/index/inwardTruck/" + inwardTruckId);
        return modelAndView;
    }

    /**
     * Delete all action
     *
     * @return the model and view
     */
    @GetMapping("/delete-all/{inwardTruckId}")
    public ModelAndView deleteAll(
            @PathVariable("inwardTruckId") Integer inwardTruckId,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardTruckBag delete all action called on id {}", inwardTruckId);
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<InwardTruck> inwardTruck = inwardTruckService.get(inwardTruckId);
            if (inwardTruck.isPresent()) {
                List<InwardTruckBag> inwardTruckBagList = inwardTruckBagService.getByInwardTruck(inwardTruck.get());
                for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
                    inwardTruckBagService.delete(inwardTruckBag.getId());
                }
            }
            LOGGER.info("InwardTruckBag delete all action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
            modelAndView.addObject("error", e);
            modelAndView.setViewName("error/500");
        }

        modelAndView.setViewName("redirect:/admin/inward-truck-bag/index/inwardTruck/" + inwardTruckId);
        return modelAndView;
    }

}
