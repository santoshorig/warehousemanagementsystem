package com.kamadhenu.warehousemanagementsystem.controller.db.location;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.location.RegionForm;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.DistrictService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <h1>Region controller</h1>
 * <p>
 * This region controller class provides the CRUD actions for regions
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/region")
public class RegionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    private RegionService regionService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Region index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Region> regionList = regionService.getAll();
        modelAndView.addObject("models", regionList);
        modelAndView.setViewName("admin/location/region/index");
        LOGGER.info("Region index action returned {} models", regionList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Region edit action called");
        ModelAndView modelAndView = new ModelAndView();
        RegionForm model = new RegionForm();
        List<District> existingDistrictList = new ArrayList<>();
        List<Location> existingLocationList = new ArrayList<>();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Region edit action called on existing model with id {}", identifier);
            Optional<Region> region = regionService.get(identifier);
            if (region.isPresent()) {
                Region regionModel = region.get();
                model.setRegion(regionModel);
                Set<Location> existingLocationSet = regionModel.getLocation();
                for (Location location : existingLocationSet) {
                    existingDistrictList.add(location.getDistrict());
                    existingLocationList.add(location);
                    model.setState(location.getDistrict().getState());
                }
                model.setDistrict(existingDistrictList);
            }
        }

        List<BusinessType> businessTypeList = businessTypeService.getAll();
        List<State> stateList = stateService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("states", stateList);
        modelAndView.addObject("districts", existingDistrictList);
        modelAndView.addObject("locations", existingLocationList);
        modelAndView.setViewName("admin/location/region/edit");
        LOGGER.info("Region edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") RegionForm region,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Region save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/location/region/edit");
        if (!result.hasErrors()) {
            try {
                regionService.edit(region.getRegion());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Region save action successful");
                modelAndView.setViewName("redirect:/admin/region/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Region save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Region delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            regionService.delete(id);
            LOGGER.info("Region delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/region/index");
        return modelAndView;
    }

}
