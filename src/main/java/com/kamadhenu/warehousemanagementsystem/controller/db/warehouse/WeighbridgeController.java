package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Weighbridge;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WeighbridgeType;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WeighbridgeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WeighbridgeTypeService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
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
 * <h1>Weighbridge controller</h1>
 * <p>
 * This weighbridge controller class provides the CRUD actions for weighbridges
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/weighbridge")
public class WeighbridgeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeighbridgeController.class);

    @Autowired
    private WeighbridgeService weighbridgeService;

    @Autowired
    private WeighbridgeTypeService weighbridgeTypeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Weighbridge index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Weighbridge> weighbridgeList = weighbridgeService.getAll();
        modelAndView.addObject("models", weighbridgeList);
        modelAndView.setViewName("admin/warehouse/weighbridge/index");
        LOGGER.info("Weighbridge index action returned {} models", weighbridgeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Weighbridge edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Weighbridge model = new Weighbridge();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Weighbridge edit action called on existing model with id {}", identifier);
            Optional<Weighbridge> weighbridge = weighbridgeService.get(identifier);
            if (weighbridge.isPresent()) {
                model = weighbridge.get();
            }
        }

        List<WeighbridgeType> weighbridgeTypeList = weighbridgeTypeService.getAll();
        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("status", constantService.getWEIGHBRIDGE_STATUS());
        modelAndView.addObject("weighbridgeTypes", weighbridgeTypeList);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/warehouse/weighbridge/edit");
        LOGGER.info("Weighbridge edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Weighbridge weighbridge,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Weighbridge save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/weighbridge/edit");
        if (!result.hasErrors()) {
            try {
                weighbridgeService.edit(weighbridge);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Weighbridge save action successful");
                modelAndView.setViewName("redirect:/admin/weighbridge/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Weighbridge save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Weighbridge delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            weighbridgeService.delete(id);
            LOGGER.info("Weighbridge delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/weighbridge/index");
        return modelAndView;
    }

}
