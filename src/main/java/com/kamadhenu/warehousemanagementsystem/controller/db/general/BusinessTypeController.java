package com.kamadhenu.warehousemanagementsystem.controller.db.general;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
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
 * <h1>BusinessType controller</h1>
 * <p>
 * This business type controller class provides the CRUD actions for business types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/business-type")
public class BusinessTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessTypeController.class);

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
        LOGGER.info("BusinessType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<BusinessType> businessTypeList = businessTypeService.getAll();
        modelAndView.addObject("models", businessTypeList);
        modelAndView.setViewName("admin/general/business-type/index");
        LOGGER.info("BusinessType index action returned {} models", businessTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("BusinessType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        BusinessType model = new BusinessType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("BusinessType edit action called on existing model with id {}", identifier);
            Optional<BusinessType> businessType = businessTypeService.get(identifier);
            if (businessType.isPresent()) {
                model = businessType.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/general/business-type/edit");
        LOGGER.info("Bank edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") BusinessType businessType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("BusinessType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/general/business-type/edit");
        if (!result.hasErrors()) {
            try {
                businessTypeService.edit(businessType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("BusinessType save action successful");
                modelAndView.setViewName("redirect:/admin/business-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("BusinessType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("BusinessType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            businessTypeService.delete(id);
            LOGGER.info("BusinessType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/business-type/index");
        return modelAndView;
    }

}
