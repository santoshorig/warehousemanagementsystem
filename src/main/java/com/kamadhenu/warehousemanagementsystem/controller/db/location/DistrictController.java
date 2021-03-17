package com.kamadhenu.warehousemanagementsystem.controller.db.location;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import com.kamadhenu.warehousemanagementsystem.service.db.location.DistrictService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.StateService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>District controller</h1>
 * <p>
 * This district controller class provides the CRUD actions for districts
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/district")
public class DistrictController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictController.class);

    @Autowired
    private DistrictService districtService;

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
        LOGGER.info("District index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<District> districtList = districtService.getAll();
        modelAndView.addObject("models", districtList);
        modelAndView.setViewName("admin/location/district/index");
        LOGGER.info("District index action returned {} models", districtList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("District edit action called");
        ModelAndView modelAndView = new ModelAndView();
        District model = new District();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("District edit action called on existing model with id {}", identifier);
            Optional<District> district = districtService.get(identifier);
            if (district.isPresent()) {
                model = district.get();
            }
        }

        List<State> stateList = stateService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("states", stateList);
        modelAndView.setViewName("admin/location/district/edit");
        LOGGER.info("District edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") District district,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("District save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/location/district/edit");
        if (!result.hasErrors()) {
            try {
                districtService.edit(district);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("District save action successful");
                modelAndView.setViewName("redirect:/admin/district/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("District save action had errors {}", result.getAllErrors());
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
        LOGGER.info("District delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            districtService.delete(id);
            LOGGER.info("District delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/district/index");
        return modelAndView;
    }

    /**
     * Get district by state action
     *
     * @param id the id of state
     * @return json
     */
    @RequestMapping(value = "/getByState/{id}", method = RequestMethod.GET)
    public ResponseEntity getByState(@PathVariable Integer id) {
        LOGGER.info("District getByState action called on id {}", id);
        Optional<State> state = stateService.get(id);
        List<District> districtList = new ArrayList<>();
        if (state.isPresent()) {
            State model = state.get();
            districtList = districtService.getByState(model);
        }
        return new ResponseEntity(districtList, HttpStatus.OK);
    }
}
