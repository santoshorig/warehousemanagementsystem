package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.BasisOfTakeoverService;
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
 * <h1>BasisOfTakeover controller</h1>
 * <p>
 * This basis of takeover controller class provides the CRUD actions for basis of takeovers
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/basis-of-takeover")
public class BasisOfTakeoverController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasisOfTakeoverController.class);

    @Autowired
    private BasisOfTakeoverService basisOfTakeoverService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("BasisOfTakeover index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<BasisOfTakeover> basisOfTakeoverList = basisOfTakeoverService.getAll();
        modelAndView.addObject("models", basisOfTakeoverList);
        modelAndView.setViewName("admin/warehouse/basis-of-takeover/index");
        LOGGER.info("BasisOfTakeover index action returned {} models", basisOfTakeoverList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("BasisOfTakeover edit action called");
        ModelAndView modelAndView = new ModelAndView();
        BasisOfTakeover model = new BasisOfTakeover();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("BasisOfTakeover edit action called on existing model with id {}", identifier);
            Optional<BasisOfTakeover> basisOfTakeover = basisOfTakeoverService.get(identifier);
            if (basisOfTakeover.isPresent()) {
                model = basisOfTakeover.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/basis-of-takeover/edit");
        LOGGER.info("BasisOfTakeover edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") BasisOfTakeover basisOfTakeover,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("BasisOfTakeover save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/basis-of-takeover/edit");
        if (!result.hasErrors()) {
            try {
                basisOfTakeoverService.edit(basisOfTakeover);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("BasisOfTakeover save action successful");
                modelAndView.setViewName("redirect:/admin/basis-of-takeover/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("BasisOfTakeover save action had errors {}", result.getAllErrors());
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
        LOGGER.info("BasisOfTakeover delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            basisOfTakeoverService.delete(id);
            LOGGER.info("BasisOfTakeover delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/basis-of-takeover/index");
        return modelAndView;
    }

}
