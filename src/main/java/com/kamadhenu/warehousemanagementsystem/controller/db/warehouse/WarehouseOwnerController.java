package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.StatusOfParty;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseOwner;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.StatusOfPartyService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseOwnerService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>WarehouseOwner controller</h1>
 * <p>
 * This warehouse owner controller class provides the CRUD actions for warehouse owners
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-owner")
public class WarehouseOwnerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseOwnerController.class);

    @Autowired
    private WarehouseOwnerService warehouseOwnerService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StatusOfPartyService statusOfPartyService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/warehouse/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseOwner index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseOwner> warehouseOwnerList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseOwnerList = warehouseOwnerService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseOwnerList.size() > 0) {
            modelAndView.addObject("models", warehouseOwnerList);
            modelAndView.setViewName("admin/warehouse/warehouse-owner/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-owner/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseOwner index action returned {} models", warehouseOwnerList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/warehouse/{warehouseId}", "/edit/warehouse/{warehouseId}/{id}"})
    public ModelAndView edit(
            @PathVariable("warehouseId") Integer warehouseId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("WarehouseOwner edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseOwner model = new WarehouseOwner();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseOwner edit action called on existing model with id {}", identifier);
                Optional<WarehouseOwner> warehouseOwner = warehouseOwnerService.get(identifier);
                if (warehouseOwner.isPresent()) {
                    model = warehouseOwner.get();
                }
            }
            model.setWarehouse(warehouseModel);
        }

        List<Location> locationList = locationService.getAll();
        List<StatusOfParty> statusOfPartyList = statusOfPartyService.getAll();
        List<String> titleList = constantService.getTITLES();
        modelAndView.addObject("model", model);
        modelAndView.addObject("locations", locationList);
        modelAndView.addObject("statusOfParties", statusOfPartyList);
        modelAndView.addObject("titles", titleList);
        modelAndView.setViewName("admin/warehouse/warehouse-owner/edit");
        LOGGER.info("WarehouseOwner edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseOwner warehouseOwner,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseOwner save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-owner/edit");
        if (!result.hasErrors()) {
            try {
                warehouseOwnerService.edit(warehouseOwner);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseOwner save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-owner/index/warehouse/" + warehouseOwner
                        .getWarehouse()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseOwner save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseOwner delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseOwner> warehouseOwner = warehouseOwnerService.get(id);
            if (warehouseOwner.isPresent()) {
                WarehouseOwner model = warehouseOwner.get();
                warehouseId = model.getWarehouse().getId();
                warehouseOwnerService.delete(id);
                LOGGER.info("WarehouseOwner delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-owner/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
