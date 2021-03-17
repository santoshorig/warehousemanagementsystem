package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Consumable;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.ConsumableService;
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
import java.util.List;
import java.util.Optional;

/**
 * <h1>Consumable controller</h1>
 * <p>
 * This consumable controller class provides the CRUD actions for consumables
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/consumable")
public class ConsumableController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumableController.class);

    @Autowired
    private ConsumableService consumableService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Consumable index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Consumable> consumableList = consumableService.getAll();
        modelAndView.addObject("models", consumableList);
        modelAndView.setViewName("admin/warehouse/consumable/index");
        LOGGER.info("Consumable index action returned {} models", consumableList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Consumable edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Consumable model = new Consumable();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Consumable edit action called on existing model with id {}", identifier);
            Optional<Consumable> consumable = consumableService.get(identifier);
            if (consumable.isPresent()) {
                model = consumable.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/consumable/edit");
        LOGGER.info("Consumable edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Consumable consumable,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Consumable save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/consumable/edit");
        if (!result.hasErrors()) {
            try {
                consumableService.edit(consumable);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Consumable save action successful");
                modelAndView.setViewName("redirect:/admin/consumable/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Consumable save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Consumable delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            consumableService.delete(id);
            LOGGER.info("Consumable delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
            modelAndView.addObject("error", e);
            modelAndView.setViewName("error/500");
        }

        modelAndView.setViewName("redirect:/admin/consumable/index");
        return modelAndView;
    }

}
