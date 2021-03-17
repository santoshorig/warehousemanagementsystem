package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseType;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseTypeService;
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
 * <h1>WarehouseType controller</h1>
 * <p>
 * This warehouse type controller class provides the CRUD actions for warehouse types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-type")
public class WarehouseTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseTypeController.class);

    @Autowired
    private WarehouseTypeService warehouseTypeService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("WarehouseType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseType> warehouseTypeList = warehouseTypeService.getAll();
        modelAndView.addObject("models", warehouseTypeList);
        modelAndView.setViewName("admin/warehouse/warehouse-type/index");
        LOGGER.info("WarehouseType index action returned {} models", warehouseTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("WarehouseType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseType model = new WarehouseType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("WarehouseType edit action called on existing model with id {}", identifier);
            Optional<WarehouseType> warehouseType = warehouseTypeService.get(identifier);
            if (warehouseType.isPresent()) {
                model = warehouseType.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-type/edit");
        LOGGER.info("WarehouseType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseType warehouseType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-type/edit");
        if (!result.hasErrors()) {
            try {
                warehouseTypeService.edit(warehouseType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseType save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseTypeService.delete(id);
            LOGGER.info("WarehouseType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-type/index");
        return modelAndView;
    }

}
