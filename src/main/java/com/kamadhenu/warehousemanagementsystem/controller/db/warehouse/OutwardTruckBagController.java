package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardTruckBagForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoInwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardTruckService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>OutwardTruckBag controller</h1>
 * <p>
 * This outward truck bag controller class provides the CRUD actions for outward truck bags
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward-truck-bag")
public class OutwardTruckBagController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardTruckBagController.class);

    @Autowired
    private OutwardTruckBagService outwardTruckBagService;

    @Autowired
    private OutwardTruckService outwardTruckService;

    @Autowired
    private DoInwardTruckBagService doInwardTruckBagService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/outwardTruck/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardTruckBag index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<OutwardTruckBag> outwardTruckBagList = new ArrayList<>();
        Optional<OutwardTruck> outwardTruck = outwardTruckService.get(id);
        if (outwardTruck.isPresent()) {
            OutwardTruck outwardTruckModel = outwardTruck.get();
            outwardTruckBagList = outwardTruckBagService.getByOutwardTruck(outwardTruckModel);
            // Calculate how many more bags/bales/drums can be allowed
            Integer allowedBagsBalesDrums = 0;
            Integer totalBagsBalesDrumsAllowed = outwardTruckModel.getTotalBagsBalesDrums();
            if (outwardTruckBagList.size() > 0) {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed - outwardTruckBagList.size();
            } else {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed;
            }
            modelAndView.addObject("allowedBagsBalesDrums", allowedBagsBalesDrums);
            modelAndView.addObject("outwardTruck", outwardTruckModel);
        }
        if (outwardTruckBagList.size() > 0) {
            modelAndView.addObject("models", outwardTruckBagList);
            modelAndView.setViewName("admin/warehouse/outward-truck-bag/index");
        } else {
            modelAndView.setViewName("redirect:/admin/outward-truck-bag/edit/outwardTruck/" + id);
        }
        LOGGER.info("OutwardTruckBag index action returned {} models", outwardTruckBagList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/outwardTruck/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardTruckBag edit action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardTruckBagForm model = new OutwardTruckBagForm();
        Integer allowedBagsBalesDrums = 0;
        List<DoInwardTruckBag> allowedDoInwardTruckBagList = new ArrayList<>();
        Optional<OutwardTruck> outwardTruck = outwardTruckService.get(id);
        if (outwardTruck.isPresent()) {
            OutwardTruck outwardTruckModel = outwardTruck.get();
            model.setOutwardTruck(outwardTruckModel);
            allowedDoInwardTruckBagList =
                    doInwardTruckBagService.getForOutwardByDo(outwardTruckModel.getOutward().getDoModel());
            // Calculate how many more bags/bales/drums can be allowed
            Integer totalBagsBalesDrumsAllowed = outwardTruckModel.getTotalBagsBalesDrums();
            List<OutwardTruckBag> outwardTruckBagList = outwardTruckBagService.getByOutwardTruck(outwardTruckModel);
            List<DoInwardTruckBag> doInwardTruckBagList = new ArrayList<>();
            if (outwardTruckBagList.size() > 0) {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed - outwardTruckBagList.size();
                if (allowedBagsBalesDrums <= 0) {
                    modelAndView.setViewName("redirect:/admin/outward-truck-bag/index/outwardTruck/" + outwardTruckModel
                            .getId());
                    return modelAndView;
                } else {
                    for (OutwardTruckBag outwardTruckBag : outwardTruckBagList) {
                        doInwardTruckBagList.add(outwardTruckBag.getDoInwardTruckBag());
                    }
                }
                model.setDoInwardTruckBagList(doInwardTruckBagList);
            } else {
                allowedBagsBalesDrums = totalBagsBalesDrumsAllowed;
            }
        } else {
            modelAndView.addObject("error", "outward not found");
            modelAndView.setViewName("error/500");
            return modelAndView;
        }

        modelAndView.addObject("model", model);
        modelAndView.addObject("allowedBagsBalesDrums", allowedBagsBalesDrums);
        modelAndView.addObject("allowedDoInwardTruckBags", allowedDoInwardTruckBagList);
        modelAndView.setViewName("admin/warehouse/outward-truck-bag/edit");
        LOGGER.info("OutwardTruckBag edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardTruckBagForm outwardTruckBagForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("OutwardTruckBag save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/outward-truck-bag/edit");
        if (!result.hasErrors()) {
            try {
                for (DoInwardTruckBag doInwardTruckBag : outwardTruckBagForm.getDoInwardTruckBagList()) {
                    OutwardTruckBag outwardTruckBag = new OutwardTruckBag();
                    outwardTruckBag.setOutwardTruck(outwardTruckBagForm.getOutwardTruck());
                    outwardTruckBag.setDoInwardTruckBag(doInwardTruckBag);
                    outwardTruckBagService.edit(outwardTruckBag);
                    doInwardTruckBag.setOutward(true);
                    doInwardTruckBagService.edit(doInwardTruckBag);
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("OutwardTruckBag save action successful");
                modelAndView.setViewName("redirect:/admin/outward-truck-bag/index/outwardTruck/" + outwardTruckBagForm
                        .getOutwardTruck()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("OutwardTruckBag save action had errors {}", result.getAllErrors());
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
        LOGGER.info("OutwardTruckBag delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer outwardTruckId = null;
        try {
            Optional<OutwardTruckBag> outwardTruckBag = outwardTruckBagService.get(id);
            if (outwardTruckBag.isPresent()) {
                OutwardTruckBag model = outwardTruckBag.get();
                DoInwardTruckBag doInwardTruckBag = model.getDoInwardTruckBag();
                outwardTruckId = model.getOutwardTruck().getId();
                outwardTruckBagService.delete(id);
                doInwardTruckBag.setOutward(false);
                doInwardTruckBagService.edit(doInwardTruckBag);
                LOGGER.info("OutwardTruckBag delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-truck-bag/index/outwardTruck/" + outwardTruckId);
        return modelAndView;
    }

    /**
     * Delete all action
     *
     * @return the model and view
     */
    @GetMapping("/delete-all/{outwardTruckId}")
    public ModelAndView deleteAll(
            @PathVariable("outwardTruckId") Integer outwardTruckId,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("OutwardTruckBag delete all action called on id {}", outwardTruckId);
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<OutwardTruck> outwardTruck = outwardTruckService.get(outwardTruckId);
            if (outwardTruck.isPresent()) {
                List<OutwardTruckBag> outwardTruckBagList =
                        outwardTruckBagService.getByOutwardTruck(outwardTruck.get());
                for (OutwardTruckBag outwardTruckBag : outwardTruckBagList) {
                    DoInwardTruckBag doInwardTruckBag = outwardTruckBag.getDoInwardTruckBag();
                    outwardTruckBagService.delete(outwardTruckBag.getId());
                    doInwardTruckBag.setOutward(false);
                    doInwardTruckBagService.edit(doInwardTruckBag);
                }
            }
            LOGGER.info("OutwardTruckBag delete all action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-truck-bag/index/outwardTruck/" + outwardTruckId);
        return modelAndView;
    }

}
