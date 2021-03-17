package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WeighbridgeType;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WeighbridgeTypeService;
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
 * <h1>WeighbridgeType controller</h1>
 * <p>
 * This weighbridge type controller class provides the CRUD actions for weighbridge types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/weighbridge-type")
public class WeighbridgeTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeighbridgeTypeController.class);

    @Autowired
    private WeighbridgeTypeService warehouseTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("WeighbridgeType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WeighbridgeType> warehouseTypeList = warehouseTypeService.getAll();
        modelAndView.addObject("models", warehouseTypeList);
        modelAndView.setViewName("admin/warehouse/weighbridge-type/index");
        LOGGER.info("WeighbridgeType index action returned {} models", warehouseTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("WeighbridgeType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WeighbridgeType model = new WeighbridgeType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("WeighbridgeType edit action called on existing model with id {}", identifier);
            Optional<WeighbridgeType> warehouseType = warehouseTypeService.get(identifier);
            if (warehouseType.isPresent()) {
                model = warehouseType.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/weighbridge-type/edit");
        LOGGER.info("WeighbridgeType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WeighbridgeType warehouseType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WeighbridgeType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/weighbridge-type/edit");
        if (!result.hasErrors()) {
            try {
                warehouseTypeService.edit(warehouseType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WeighbridgeType save action successful");
                modelAndView.setViewName("redirect:/admin/weighbridge-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WeighbridgeType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WeighbridgeType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseTypeService.delete(id);
            LOGGER.info("WeighbridgeType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/weighbridge-type/index");
        return modelAndView;
    }

}
