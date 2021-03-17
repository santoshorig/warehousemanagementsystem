package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.MaterialOwner;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.MaterialOwnerService;
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
 * <h1>MaterialOwner controller</h1>
 * <p>
 * This material owner controller class provides the CRUD actions for material owners
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/material-owner")
public class MaterialOwnerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialOwnerController.class);

    @Autowired
    private MaterialOwnerService materialOwnerService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("MaterialOwner index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<MaterialOwner> materialOwnerList = materialOwnerService.getAll();
        modelAndView.addObject("models", materialOwnerList);
        modelAndView.setViewName("admin/warehouse/material-owner/index");
        LOGGER.info("MaterialOwner index action returned {} models", materialOwnerList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("MaterialOwner edit action called");
        ModelAndView modelAndView = new ModelAndView();
        MaterialOwner model = new MaterialOwner();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("MaterialOwner edit action called on existing model with id {}", identifier);
            Optional<MaterialOwner> materialOwner = materialOwnerService.get(identifier);
            if (materialOwner.isPresent()) {
                model = materialOwner.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/material-owner/edit");
        LOGGER.info("MaterialOwner edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") MaterialOwner materialOwner,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("MaterialOwner save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/material-owner/edit");
        if (!result.hasErrors()) {
            try {
                materialOwnerService.edit(materialOwner);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("MaterialOwner save action successful");
                modelAndView.setViewName("redirect:/admin/material-owner/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("MaterialOwner save action had errors {}", result.getAllErrors());
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
        LOGGER.info("MaterialOwner delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            materialOwnerService.delete(id);
            LOGGER.info("MaterialOwner delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/material-owner/index");
        return modelAndView;
    }

}
