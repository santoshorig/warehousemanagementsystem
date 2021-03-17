package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Manpower;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.ManpowerService;
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
 * <h1>Manpower controller</h1>
 * <p>
 * This manpower controller class provides the CRUD actions for manpowers
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/manpower")
public class ManpowerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManpowerController.class);

    @Autowired
    private ManpowerService manpowerService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Manpower index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Manpower> manpowerList = manpowerService.getAll();
        modelAndView.addObject("models", manpowerList);
        modelAndView.setViewName("admin/warehouse/manpower/index");
        LOGGER.info("Manpower index action returned {} models", manpowerList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Manpower edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Manpower model = new Manpower();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Manpower edit action called on existing model with id {}", identifier);
            Optional<Manpower> manpower = manpowerService.get(identifier);
            if (manpower.isPresent()) {
                model = manpower.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/manpower/edit");
        LOGGER.info("Manpower edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Manpower manpower,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Manpower save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/manpower/edit");
        if (!result.hasErrors()) {
            try {
                manpowerService.edit(manpower);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Manpower save action successful");
                modelAndView.setViewName("redirect:/admin/manpower/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Manpower save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Manpower delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            manpowerService.delete(id);
            LOGGER.info("Manpower delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/manpower/index");
        return modelAndView;
    }
}
