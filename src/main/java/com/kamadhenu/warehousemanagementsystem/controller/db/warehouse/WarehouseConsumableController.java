package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Consumable;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseConsumable;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.ConsumableService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseConsumableService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDetailService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
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
 * <h1>WarehouseConsumable controller</h1>
 * <p>
 * This warehouse consumable controller class provides the CRUD actions for warehouse consumables
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-consumable")
public class WarehouseConsumableController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseConsumableController.class);

    @Autowired
    private WarehouseConsumableService warehouseConsumableService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @Autowired
    private ConsumableService consumableService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/warehouse/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseConsumable index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseConsumable> warehouseConsumableList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseConsumableList = warehouseConsumableService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseConsumableList.size() > 0) {
            modelAndView.addObject("models", warehouseConsumableList);
            modelAndView.setViewName("admin/warehouse/warehouse-consumable/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-consumable/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseConsumable index action returned {} models", warehouseConsumableList.size());
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
        LOGGER.info("WarehouseConsumable edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseConsumable model = new WarehouseConsumable();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseConsumable edit action called on existing model with id {}", identifier);
                Optional<WarehouseConsumable> warehouseConsumable = warehouseConsumableService.get(identifier);
                if (warehouseConsumable.isPresent()) {
                    model = warehouseConsumable.get();
                }
            }
            model.setWarehouse(warehouseModel);
        }

        List<Warehouse> warehouseList = warehouseService.getAll();
        List<Warehouse> applicableWarehouseList = new ArrayList<>();
        for (Warehouse warehouseModel : warehouseList) {
            if (warehouseModel.getId() != warehouseId) {
                applicableWarehouseList.add(warehouseModel);
            }
        }
        List<Consumable> consumableList = consumableService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("warehouses", applicableWarehouseList);
        modelAndView.addObject("consumableTypes", consumableList);
        modelAndView.setViewName("admin/warehouse/warehouse-consumable/edit");
        LOGGER.info("WarehouseConsumable edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseConsumable warehouseConsumable,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseConsumable save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-consumable/edit");
        if (!result.hasErrors()) {
            try {
                warehouseConsumableService.edit(warehouseConsumable);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseConsumable save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-consumable/index/warehouse/" + warehouseConsumable
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
            LOGGER.error("WarehouseConsumable save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseConsumable delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseConsumable> warehouseConsumable = warehouseConsumableService.get(id);
            if (warehouseConsumable.isPresent()) {
                WarehouseConsumable model = warehouseConsumable.get();
                warehouseId = model.getWarehouse().getId();
                warehouseConsumableService.delete(id);
                LOGGER.info("WarehouseConsumable delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-consumable/index/warehouse/" + warehouseId);
        return modelAndView;
    }

    /**
     * Get mtf recommendation by consumable and warehouse action
     *
     * @param warehouseId  the id of warehouse
     * @param consumableId the id of asset
     * @return json
     */
    @RequestMapping(value = "/getMtfRecommendationByWarehouseAndConsumable/{warehouseId}/{consumableId}", method = RequestMethod.GET)
    public ResponseEntity getMtfRecommendationByWarehouseAndConsumable(
            @PathVariable("warehouseId") Integer warehouseId,
            @PathVariable("consumableId") Integer consumableId
    ) {
        Integer mtfRecommendation = 0;
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            List<WarehouseDetail> warehouseDetailList = warehouseDetailService.getByWarehouse(warehouseModel);
            if (warehouseDetailList.size() > 0) {
                WarehouseDetail warehouseDetail = warehouseDetailList.get(0);
                Optional<Consumable> consumable = consumableService.get(consumableId);
                if (consumable.isPresent()) {
                    Consumable consumableModel = consumable.get();
                    Double mtf = consumableModel.getMtfRecommendation() * warehouseDetail.getArea();
                    mtfRecommendation = Math.round(mtf.intValue());
                }
            }
        }

        return new ResponseEntity(mtfRecommendation, HttpStatus.OK);
    }
}
