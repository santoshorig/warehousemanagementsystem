package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TakeoverType;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TakeoverTypeService;
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
 * <h1>TakeoverType controller</h1>
 * <p>
 * This takeover type controller class provides the CRUD actions for takeover types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/takeover-type")
public class TakeoverTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TakeoverTypeController.class);

    @Autowired
    private TakeoverTypeService takeoverTypeService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("TakeoverType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<TakeoverType> takeoverTypeList = takeoverTypeService.getAll();
        modelAndView.addObject("models", takeoverTypeList);
        modelAndView.setViewName("admin/warehouse/takeover-type/index");
        LOGGER.info("TakeoverType index action returned {} models", takeoverTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("TakeoverType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        TakeoverType model = new TakeoverType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("TakeoverType edit action called on existing model with id {}", identifier);
            Optional<TakeoverType> takeoverType = takeoverTypeService.get(identifier);
            if (takeoverType.isPresent()) {
                model = takeoverType.get();
            }
        }

        List<BusinessType> businessTypeList = businessTypeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/warehouse/takeover-type/edit");
        LOGGER.info("TakeoverType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") TakeoverType takeoverType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("TakeoverType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/takeover-type/edit");
        if (!result.hasErrors()) {
            try {
                takeoverTypeService.edit(takeoverType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("TakeoverType save action successful");
                modelAndView.setViewName("redirect:/admin/takeover-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("TakeoverType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("TakeoverType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            takeoverTypeService.delete(id);
            LOGGER.info("TakeoverType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/takeover-type/index");
        return modelAndView;
    }

}
