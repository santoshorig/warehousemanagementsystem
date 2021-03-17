package com.kamadhenu.warehousemanagementsystem.controller.db.location;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import com.kamadhenu.warehousemanagementsystem.service.db.location.StateService;
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
 * <h1>State controller</h1>
 * <p>
 * This state controller class provides the CRUD actions for states
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/state")
public class StateController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateController.class);

    @Autowired
    private StateService stateService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("State index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<State> stateList = stateService.getAll();
        modelAndView.addObject("models", stateList);
        modelAndView.setViewName("admin/location/state/index");
        LOGGER.info("State index action returned {} models", stateList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("State edit action called");
        ModelAndView modelAndView = new ModelAndView();
        State model = new State();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("State edit action called on existing model with id {}", identifier);
            Optional<State> state = stateService.get(identifier);
            if (state.isPresent()) {
                model = state.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/location/state/edit");
        LOGGER.info("State edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") State state,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("State save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/location/state/edit");
        if (!result.hasErrors()) {
            try {
                stateService.edit(state);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("State save action successful");
                modelAndView.setViewName("redirect:/admin/state/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("State save action had errors {}", result.getAllErrors());
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
        LOGGER.info("State delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            stateService.delete(id);
            LOGGER.info("State delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/state/index");
        return modelAndView;
    }

}
