package com.kamadhenu.warehousemanagementsystem.controller.db.location;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.location.DistrictService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.MapsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Location controller</h1>
 * <p>
 * This location controller class provides the CRUD actions for locations
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/location")
public class LocationController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private MapsService mapsService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Location index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("models", locationList);
        modelAndView.setViewName("admin/location/location/index");
        LOGGER.info("Location index action returned {} models", locationList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Location edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Location model = new Location();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Location edit action called on existing model with id {}", identifier);
            Optional<Location> location = locationService.get(identifier);
            if (location.isPresent()) {
                model = location.get();
            }
        }

        List<District> districtList = districtService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("districts", districtList);
        modelAndView.setViewName("admin/location/location/edit");
        LOGGER.info("Location edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Location location,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Location save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/location/location/edit");
        if (!result.hasErrors()) {
            try {
                locationService.edit(location);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Location save action successful");
                modelAndView.setViewName("redirect:/admin/location/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Location save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Location delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            locationService.delete(id);
            LOGGER.info("Location delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/location/index");
        return modelAndView;
    }

    /**
     * Get locations by districts
     *
     * @param districts
     * @param errors
     * @return
     */
    @RequestMapping(value = "/getByDistricts", method = RequestMethod.POST)
    public ResponseEntity getByDistricts(@Valid @RequestBody List<Integer> districts, Errors errors) {
        LOGGER.info("Location getByDistricts action called on ids {}", districts);
        List<Location> locationList = new ArrayList<>();
        if (!errors.hasErrors()) {
            if (districts.size() > 0) {
                List<District> districtList = new ArrayList<>();
                for (Integer district : districts) {
                    Optional<District> districtModel = districtService.get(district);
                    if (districtModel.isPresent()) {
                        districtList.add(districtModel.get());
                    }
                }
                locationList = locationService.getByDistricts(districtList);
            }
        } else {
            LOGGER.error("Location getByDistricts action had errors {}", errors.getAllErrors());
        }
        return new ResponseEntity(locationList, HttpStatus.OK);
    }

    /**
     * Update latitude and longitude action
     *
     * @return the model and view
     */
    @GetMapping("/update-lat-lon/{id}")
    public ModelAndView updateLatLon(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("Location update latitude and longitude action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<Location> location = locationService.get(id);
            if (location.isPresent()) {
                Location model = location.get();
                mapsService.updateGeoCoding(model);
            }
            LOGGER.info("Location update latitude and longitude action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/location/index");
        return modelAndView;
    }

    /**
     * Update all latitude and longitude action
     *
     * @return the model and view
     */
    @GetMapping("/update-all-lat-lon")
    public ModelAndView updateAllLatLon(RedirectAttributes redirectAttributes) {
        LOGGER.info("All location update latitude and longitude action called");
        ModelAndView modelAndView = new ModelAndView();
        try {
            mapsService.updateGeoCoding();
            LOGGER.info("Location update latitude and longitude action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/location/index");
        return modelAndView;
    }
}
