package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardMadeUpBagForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardMadeUpBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseStackService;
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
 * <h1>InwardMadeUpBag controller</h1>
 * <p>
 * This inward made up bag controller class provides the CRUD actions for inward made up bags
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-made-up-bag")
public class InwardMadeUpBagController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardMadeUpBagController.class);

    @Autowired
    private InwardMadeUpBagService inwardMadeUpBagService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private WarehouseStackService warehouseStackService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/inward/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("InwardMadeUpBag index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InwardMadeUpBag> inwardMadeUpBagList = new ArrayList<>();
        Optional<Inward> inward = inwardService.get(id);
        if (inward.isPresent()) {
            Inward inwardModel = inward.get();
            inwardMadeUpBagList = inwardMadeUpBagService.getByInward(inwardModel);
            modelAndView.addObject("inward", inwardModel);
        }
        if (inwardMadeUpBagList.size() > 0) {
            modelAndView.addObject("models", inwardMadeUpBagList);
            modelAndView.setViewName("admin/warehouse/inward-made-up-bag/index");
        } else {
            modelAndView.setViewName("redirect:/admin/inward-made-up-bag/edit/inward/" + id);
        }
        LOGGER.info("InwardMadeUpBag index action returned {} models", inwardMadeUpBagList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/inward/{inwardId}", "/edit/inward/{inwardId}/{id}"})
    public ModelAndView edit(
            @PathVariable("inwardId") Integer inwardId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("InwardMadeUpBag edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardMadeUpBagForm model = new InwardMadeUpBagForm();
        Optional<Inward> inward = inwardService.get(inwardId);
        List<WarehouseStack> warehouseNonFullStackList = new ArrayList<>();
        List<WarehouseStack> warehouseStackList = new ArrayList<>();
        Map<Integer, Double> warehouseStackUsageWeight = new HashMap<>();
        Map<Integer, Integer> warehouseStackUsageCount = new HashMap<>();
        Map<Integer, Integer> warehouseMadeUpBagStackUsageCount = new HashMap<>();
        if (inward.isPresent()) {
            Inward inwardModel = inward.get();
            InwardMadeUpBag inwardMadeUpBagModel = new InwardMadeUpBag();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("InwardMadeUpBag edit action called on existing model with id {}", identifier);
                Optional<InwardMadeUpBag> inwardMadeUpBag = inwardMadeUpBagService.get(identifier);
                if (inwardMadeUpBag.isPresent()) {
                    inwardMadeUpBagModel = inwardMadeUpBag.get();
                    model.setTotalBags(1); // as only 1 bag if id is present
                }
            }
            inwardMadeUpBagModel.setOutward(false); // Set outward to false
            inwardMadeUpBagModel.setInward(inwardModel);
            model.setInwardMadeUpBag(inwardMadeUpBagModel);
            Warehouse warehouse = inwardModel.getContract().getWarehouse();
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
        }

        modelAndView.addObject("model", model);
        modelAndView.addObject("warehouseStacks", warehouseNonFullStackList);
        modelAndView.addObject("warehouseStackUsageCount", warehouseStackUsageCount);
        modelAndView.addObject("warehouseStackUsageWeight", warehouseStackUsageWeight);
        modelAndView.addObject("warehouseMadeUpBagStackUsageCount", warehouseMadeUpBagStackUsageCount);
        modelAndView.setViewName("admin/warehouse/inward-made-up-bag/edit");
        LOGGER.info("InwardMadeUpBag edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardMadeUpBagForm inwardMadeUpBagForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardMadeUpBag save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward-made-up-bag/edit");
        if (!result.hasErrors()) {
            try {
                for (Integer i = 1; i <= inwardMadeUpBagForm.getTotalBags(); i++) {
                    InwardMadeUpBag inwardMadeUpBag = new InwardMadeUpBag();
                    inwardMadeUpBag.setOutward(inwardMadeUpBagForm.getInwardMadeUpBag().getOutward());
                    inwardMadeUpBag.setWarehouseStack(inwardMadeUpBagForm.getInwardMadeUpBag().getWarehouseStack());
                    inwardMadeUpBag.setInward(inwardMadeUpBagForm.getInwardMadeUpBag().getInward());
                    inwardMadeUpBagService.edit(inwardMadeUpBag);
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InwardMadeUpBag save action successful");
                modelAndView.setViewName("redirect:/admin/inward-made-up-bag/index/inward/" + inwardMadeUpBagForm
                        .getInwardMadeUpBag()
                        .getInward()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InwardMadeUpBag save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InwardMadeUpBag delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer inwardId = null;
        try {
            Optional<InwardMadeUpBag> inwardMadeUpBag = inwardMadeUpBagService.get(id);
            if (inwardMadeUpBag.isPresent()) {
                InwardMadeUpBag model = inwardMadeUpBag.get();
                inwardId = model.getInward().getId();
                inwardMadeUpBagService.delete(id);
                LOGGER.info("InwardMadeUpBag delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward-made-up-bag/index/inward/" + inwardId);
        return modelAndView;
    }

}
