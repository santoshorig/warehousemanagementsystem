package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardMadeUpBagForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
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
 * <h1>OutwardMadeUpBag controller</h1>
 * <p>
 * This outward made up bag controller class provides the CRUD actions for outward made up bags
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward-made-up-bag")
public class OutwardMadeUpBagController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardMadeUpBagController.class);

    @Autowired
    private OutwardMadeUpBagService outwardMadeUpBagService;

    @Autowired
    private OutwardService outwardService;

    @Autowired
    private OutwardTruckBagService outwardTruckBagService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private InwardMadeUpBagService inwardMadeUpBagService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/outward/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardMadeUpBag index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<OutwardMadeUpBag> outwardMadeUpBagList = new ArrayList<>();
        Optional<Outward> outward = outwardService.get(id);
        if (outward.isPresent()) {
            Outward outwardModel = outward.get();
            outwardMadeUpBagList = outwardMadeUpBagService.getByOutward(outwardModel);
            modelAndView.addObject("outward", outwardModel);
        }
        if (outwardMadeUpBagList.size() > 0) {
            modelAndView.addObject("models", outwardMadeUpBagList);
            modelAndView.setViewName("admin/warehouse/outward-made-up-bag/index");
        } else {
            modelAndView.setViewName("redirect:/admin/outward-made-up-bag/edit/outward/" + id);
        }
        LOGGER.info("OutwardMadeUpBag index action returned {} models", outwardMadeUpBagList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/outward/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardMadeUpBag edit action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardMadeUpBagForm model = new OutwardMadeUpBagForm();
        List<InwardMadeUpBag> inwardMadeUpBagList = new ArrayList<>();
        Optional<Outward> outward = outwardService.get(id);
        if (outward.isPresent()) {
            Outward outwardModel = outward.get();
            List<OutwardMadeUpBag> outwardMadeUpBagList = outwardMadeUpBagService.getByOutward(outwardModel);
            List<InwardMadeUpBag> existingInwardMadeUpBagList = new ArrayList<>();
            for (OutwardMadeUpBag outwardMadeUpBag : outwardMadeUpBagList) {
                existingInwardMadeUpBagList.add(outwardMadeUpBag.getInwardMadeUpBag());
            }
            model.setInwardMadeUpBagList(existingInwardMadeUpBagList);
            model.setOutward(outwardModel);

            List<Inward> inwardList = inwardService.getApprovedByContract(outwardModel.getDoModel().getContract());
            for (Inward inward : inwardList) {
                inwardMadeUpBagList.addAll(inwardMadeUpBagService.getForOutwardByInward(inward));
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.addObject("allowedInwardMadeUpBags", inwardMadeUpBagList);
        modelAndView.setViewName("admin/warehouse/outward-made-up-bag/edit");
        LOGGER.info("OutwardMadeUpBag edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardMadeUpBagForm outwardMadeUpBagForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("OutwardMadeUpBag save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/outward-made-up-bag/edit");
        if (!result.hasErrors()) {
            try {
                for (InwardMadeUpBag inwardMadeUpBag : outwardMadeUpBagForm.getInwardMadeUpBagList()) {
                    OutwardMadeUpBag outwardMadeUpBag = new OutwardMadeUpBag();
                    outwardMadeUpBag.setInwardMadeUpBag(inwardMadeUpBag);
                    outwardMadeUpBag.setOutward(outwardMadeUpBag.getOutward());
                    outwardMadeUpBagService.edit(outwardMadeUpBag);
                    inwardMadeUpBag.setOutward(true);
                    inwardMadeUpBagService.edit(inwardMadeUpBag);
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("OutwardMadeUpBag save action successful");
                modelAndView.setViewName("redirect:/admin/outward-made-up-bag/index/outward/" + outwardMadeUpBagForm
                        .getOutward()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("OutwardMadeUpBag save action had errors {}", result.getAllErrors());
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
        LOGGER.info("OutwardMadeUpBag delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer outwardId = null;
        try {
            Optional<OutwardMadeUpBag> outwardMadeUpBag = outwardMadeUpBagService.get(id);
            if (outwardMadeUpBag.isPresent()) {
                OutwardMadeUpBag model = outwardMadeUpBag.get();
                outwardId = model.getOutward().getId();
                InwardMadeUpBag inwardMadeUpBag = model.getInwardMadeUpBag();
                inwardMadeUpBag.setOutward(false);
                outwardMadeUpBagService.delete(model.getId());
                inwardMadeUpBagService.edit(inwardMadeUpBag);
                LOGGER.info("OutwardMadeUpBag delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-made-up-bag/index/outward/" + outwardId);
        return modelAndView;
    }

}
