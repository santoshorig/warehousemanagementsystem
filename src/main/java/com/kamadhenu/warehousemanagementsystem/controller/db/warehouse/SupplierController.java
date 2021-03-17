package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Supplier;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SupplierService;
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
 * <h1>Supplier controller</h1>
 * <p>
 * This supplier controller class provides the CRUD actions for suppliers
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/supplier")
public class SupplierController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Supplier index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Supplier> supplierList = supplierService.getAll();
        modelAndView.addObject("models", supplierList);
        modelAndView.setViewName("admin/warehouse/supplier/index");
        LOGGER.info("Supplier index action returned {} models", supplierList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Supplier edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Supplier model = new Supplier();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Supplier edit action called on existing model with id {}", identifier);
            Optional<Supplier> supplier = supplierService.get(identifier);
            if (supplier.isPresent()) {
                model = supplier.get();
            }
        }

        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/warehouse/supplier/edit");
        LOGGER.info("Supplier edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Supplier supplier,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Supplier save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/supplier/edit");
        if (!result.hasErrors()) {
            try {
                supplierService.edit(supplier);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Supplier save action successful");
                modelAndView.setViewName("redirect:/admin/supplier/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Supplier save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Supplier delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            supplierService.delete(id);
            LOGGER.info("Supplier delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/supplier/index");
        return modelAndView;
    }

}
