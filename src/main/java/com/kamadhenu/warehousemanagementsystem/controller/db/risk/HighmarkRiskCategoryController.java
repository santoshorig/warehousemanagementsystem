package com.kamadhenu.warehousemanagementsystem.controller.db.risk;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;
import com.kamadhenu.warehousemanagementsystem.service.db.risk.HighmarkRiskCategoryService;
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
 * <h1>HighmarkRiskCategory controller</h1>
 * <p>
 * This highmark risk category controller class provides the CRUD actions for highmark risk categories
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/highmark-risk-category")
public class HighmarkRiskCategoryController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HighmarkRiskCategoryController.class);

    @Autowired
    private HighmarkRiskCategoryService highmarkRiskCategoryService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("HighmarkRiskCategory index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<HighmarkRiskCategory> highmarkRiskCategoryList = highmarkRiskCategoryService.getAll();
        modelAndView.addObject("models", highmarkRiskCategoryList);
        modelAndView.setViewName("admin/risk/highmark-risk-category/index");
        LOGGER.info("HighmarkRiskCategory index action returned {} models", highmarkRiskCategoryList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("HighmarkRiskCategory edit action called");
        ModelAndView modelAndView = new ModelAndView();
        HighmarkRiskCategory model = new HighmarkRiskCategory();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("HighmarkRiskCategory index action called on existing model with id {}", identifier);
            Optional<HighmarkRiskCategory> highmarkRiskCategory = highmarkRiskCategoryService.get(identifier);
            if (highmarkRiskCategory.isPresent()) {
                model = highmarkRiskCategory.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/risk/highmark-risk-category/edit");
        LOGGER.info("HighmarkRiskCategory edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") HighmarkRiskCategory highmarkRiskCategory,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("HighmarkRiskCategory save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/risk/highmark-risk-category/edit");
        if (!result.hasErrors()) {
            try {
                highmarkRiskCategoryService.edit(highmarkRiskCategory);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("HighmarkRiskCategory save action successful");
                modelAndView.setViewName("redirect:/admin/highmark-risk-category/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("HighmarkRiskCategory save action had errors {}", result.getAllErrors());
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
        LOGGER.info("HighmarkRiskCategory delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            highmarkRiskCategoryService.delete(id);
            LOGGER.info("HighmarkRiskCategory delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/highmark-risk-category/index");
        return modelAndView;
    }

}
