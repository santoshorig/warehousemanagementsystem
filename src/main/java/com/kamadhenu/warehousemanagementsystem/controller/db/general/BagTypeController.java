package com.kamadhenu.warehousemanagementsystem.controller.db.general;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BagTypeService;
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
 * <h1>BagType controller</h1>
 * <p>
 * This bag type controller class provides the CRUD actions for bag types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/bag-type")
public class BagTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BagTypeController.class);

    @Autowired
    private BagTypeService bagTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("BagType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<BagType> bagTypeList = bagTypeService.getAll();
        modelAndView.addObject("models", bagTypeList);
        modelAndView.setViewName("admin/general/bag-type/index");
        LOGGER.info("BagType index action returned {} models", bagTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("BagType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        BagType model = new BagType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("BagType edit action called on existing model with id {}", identifier);
            Optional<BagType> bagType = bagTypeService.get(identifier);
            if (bagType.isPresent()) {
                model = bagType.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/general/bag-type/edit");
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
            @Valid @ModelAttribute("model") BagType bagType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("BagType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/general/bag-type/edit");
        if (!result.hasErrors()) {
            try {
                bagTypeService.edit(bagType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("BagType save action successful");
                modelAndView.setViewName("redirect:/admin/bag-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("BagType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("BagType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            bagTypeService.delete(id);
            LOGGER.info("BagType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/bag-type/index");
        return modelAndView;
    }

}
