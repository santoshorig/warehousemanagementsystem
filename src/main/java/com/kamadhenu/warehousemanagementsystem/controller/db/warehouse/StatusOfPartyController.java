package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.StatusOfParty;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.StatusOfPartyService;
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
 * <h1>StatusOfParty controller</h1>
 * <p>
 * This status of party controller class provides the CRUD actions for status of parties
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/status-of-party")
public class StatusOfPartyController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusOfPartyController.class);

    @Autowired
    private StatusOfPartyService statusOfPartyService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("StatusOfParty index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<StatusOfParty> statusOfPartyList = statusOfPartyService.getAll();
        modelAndView.addObject("models", statusOfPartyList);
        modelAndView.setViewName("admin/warehouse/status-of-party/index");
        LOGGER.info("StatusOfParty index action returned {} models", statusOfPartyList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("StatusOfParty edit action called");
        ModelAndView modelAndView = new ModelAndView();
        StatusOfParty model = new StatusOfParty();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("StatusOfParty edit action called on existing model with id {}", identifier);
            Optional<StatusOfParty> statusOfParty = statusOfPartyService.get(identifier);
            if (statusOfParty.isPresent()) {
                model = statusOfParty.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/status-of-party/edit");
        LOGGER.info("StatusOfParty edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") StatusOfParty statusOfParty,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("StatusOfParty save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/status-of-party/edit");
        if (!result.hasErrors()) {
            try {
                statusOfPartyService.edit(statusOfParty);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("StatusOfParty save action successful");
                modelAndView.setViewName("redirect:/admin/status-of-party/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("StatusOfParty save action had errors {}", result.getAllErrors());
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
        LOGGER.info("StatusOfParty delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            statusOfPartyService.delete(id);
            LOGGER.info("StatusOfParty delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/status-of-party/index");
        return modelAndView;
    }

}
