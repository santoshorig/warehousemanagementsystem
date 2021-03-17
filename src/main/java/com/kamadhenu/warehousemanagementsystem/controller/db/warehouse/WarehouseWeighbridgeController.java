package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Weighbridge;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseWeighbridgeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WeighbridgeService;
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
import java.util.Map;
import java.util.Optional;

/**
 * <h1>WarehouseWeighbridge controller</h1>
 * <p>
 * This warehouse weighbridge controller class provides the CRUD actions for warehouse weighbridges
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-weighbridge")
public class WarehouseWeighbridgeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseWeighbridgeController.class);

    @Autowired
    private WarehouseWeighbridgeService warehouseWeighbridgeService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WeighbridgeService weighbridgeService;

    @Autowired
    private RegionLocationService regionLocationService;

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
        LOGGER.info("WarehouseWeighbridge index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseWeighbridge> warehouseWeighbridgeList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseWeighbridgeList = warehouseWeighbridgeService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseWeighbridgeList.size() > 0) {
            modelAndView.addObject("models", warehouseWeighbridgeList);
            modelAndView.setViewName("admin/warehouse/warehouse-weighbridge/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-weighbridge/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseWeighbridge index action returned {} models", warehouseWeighbridgeList.size());
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
        LOGGER.info("WarehouseWeighbridge edit action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Weighbridge> weighbridgeList = new ArrayList<>();
        WarehouseWeighbridge model = new WarehouseWeighbridge();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseWeighbridge edit action called on existing model with id {}", identifier);
                Optional<WarehouseWeighbridge> warehouseWeighbridge = warehouseWeighbridgeService.get(identifier);
                if (warehouseWeighbridge.isPresent()) {
                    model = warehouseWeighbridge.get();
                }
            }
            model.setWarehouse(warehouseModel);
            Optional<RegionLocation> regionLocation = regionLocationService.get(warehouseModel.getRegLoc());
            if (regionLocation.isPresent()) {
                weighbridgeList = weighbridgeService.getByLocation(regionLocation.get().getLocation());
            }
        }

        Map<String, String> weighbridgeTypeMap = constantService.getWEIGHBRIDGE_TYPE();
        Map<String, String> weighbridgeLocationMap = constantService.getWEIGHBRIDGE_LOCATION();
        modelAndView.addObject("model", model);
        modelAndView.addObject("weighbridges", weighbridgeList);
        modelAndView.addObject("weighbridgeTypes", weighbridgeTypeMap);
        modelAndView.addObject("weighbridgeLocations", weighbridgeLocationMap);
        modelAndView.setViewName("admin/warehouse/warehouse-weighbridge/edit");
        LOGGER.info("WarehouseWeighbridge edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseWeighbridge warehouseWeighbridge,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseWeighbridge save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-weighbridge/edit");
        if (!result.hasErrors()) {
            try {
                warehouseWeighbridgeService.edit(warehouseWeighbridge);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseWeighbridge save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-weighbridge/index/warehouse/" + warehouseWeighbridge
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
            LOGGER.error("WarehouseWeighbridge save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseWeighbridge delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseWeighbridge> warehouseWeighbridge = warehouseWeighbridgeService.get(id);
            if (warehouseWeighbridge.isPresent()) {
                WarehouseWeighbridge model = warehouseWeighbridge.get();
                warehouseId = model.getWarehouse().getId();
                warehouseWeighbridgeService.delete(id);
                LOGGER.info("WarehouseWeighbridge delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-weighbridge/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
