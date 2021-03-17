package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityLab;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.UtilizationFactorService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.QualityLabService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseCommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
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
import java.util.Map;
import java.util.Optional;

/**
 * <h1>WarehouseCommodity controller</h1>
 * <p>
 * This warehouse commodity controller class provides the CRUD actions for warehouse commodities
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-commodity")
public class WarehouseCommodityController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseCommodityController.class);

    @Autowired
    private WarehouseCommodityService warehouseCommodityService;

    @Autowired
    private QualityLabService qualityLabService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UtilizationFactorService utilizationFactorService;

    @Autowired
    private WarehouseService warehouseService;

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
        LOGGER.info("WarehouseCommodity index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseCommodity> warehouseCommodityList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseCommodityList = warehouseCommodityService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseCommodityList.size() > 0) {
            modelAndView.addObject("models", warehouseCommodityList);
            modelAndView.setViewName("admin/warehouse/warehouse-commodity/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-commodity/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseCommodity index action returned {} models", warehouseCommodityList.size());
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
        LOGGER.info("WarehouseCommodity edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseCommodity model = new WarehouseCommodity();
        Integer availableCapacity = 100;
        List<QualityLab> qualityLabList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseCommodity edit action called on existing model with id {}", identifier);
                Optional<WarehouseCommodity> warehouseCommodity = warehouseCommodityService.get(identifier);
                if (warehouseCommodity.isPresent()) {
                    model = warehouseCommodity.get();
                }
            }
            model.setWarehouse(warehouseModel);
            qualityLabList = qualityLabService.getAll();
            List<WarehouseCommodity> warehouseCommodityList = warehouseCommodityService.getByWarehouse(warehouseModel);
            for (WarehouseCommodity warehouseCommodity : warehouseCommodityList) {
                availableCapacity = availableCapacity - warehouseCommodity.getPlannedUtilization();
                if (model.getCommodity() != null && (warehouseCommodity.getCommodity().getId() == model.getCommodity()
                        .getId())) {
                    availableCapacity = availableCapacity + warehouseCommodity.getPlannedUtilization();
                }
            }
        }

        Map<String, String> qualityCheckByMap = constantService.getQUALITY_CHECK_BY();
        List<Commodity> commodityList = commodityService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("qualityLabs", qualityLabList);
        modelAndView.addObject("qualityCheckBy", qualityCheckByMap);
        modelAndView.addObject("availableCapacity", availableCapacity);
        modelAndView.setViewName("admin/warehouse/warehouse-commodity/edit");
        LOGGER.info("WarehouseCommodity edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseCommodity warehouseCommodity,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseCommodity save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-commodity/edit");
        if (!result.hasErrors()) {
            try {
                warehouseCommodityService.edit(warehouseCommodity);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseCommodity save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-commodity/index/warehouse/" + warehouseCommodity
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
            LOGGER.error("WarehouseCommodity save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseCommodity delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseCommodity> warehouseCommodity = warehouseCommodityService.get(id);
            if (warehouseCommodity.isPresent()) {
                WarehouseCommodity model = warehouseCommodity.get();
                warehouseId = model.getWarehouse().getId();
                warehouseCommodityService.delete(id);
                LOGGER.info("WarehouseCommodity delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-commodity/index/warehouse/" + warehouseId);
        return modelAndView;
    }

    /**
     * Get utilization factor by commodity action
     *
     * @param commodity
     * @param warehouse
     * @return json
     */
    @RequestMapping(value = "/getUtilizationFactorByCommodity/{commodity}/{warehouse}", method = RequestMethod.GET)
    public ResponseEntity getUtilizationFactorByCommodity(
            @PathVariable Integer commodity,
            @PathVariable Integer warehouse
    ) {
        Double utilizationFactor = new Double(0);
        Optional<Warehouse> warehouseModel = warehouseService.get(warehouse);
        Warehouse existingWarehouseModel = warehouseModel.get();
        if (warehouseModel.isPresent()) {
            Optional<Commodity> commodityModel = commodityService.get(commodity);
            if (commodityModel.isPresent()) {
                Commodity existingCommodityModel = commodityModel.get();
                List<UtilizationFactor> utilizationFactorList = utilizationFactorService.getByCommodityAndBusinessType(
                        existingCommodityModel,
                        existingWarehouseModel.getBusinessType()
                );
                if (utilizationFactorList.size() > 0) {
                    UtilizationFactor utilizationFactorFirst = utilizationFactorList.get(0);
                    utilizationFactor = utilizationFactorFirst.getFactor();
                }
            }
        }
        return new ResponseEntity(utilizationFactor, HttpStatus.OK);
    }

    /**
     * Get commodity by warehouse action
     *
     * @param warehouse
     * @return json
     */
    @RequestMapping(value = "/getCommodityByWarehouse/{warehouse}", method = RequestMethod.GET)
    public ResponseEntity getCommodityByWarehouse(@PathVariable Integer warehouse) {
        List<WarehouseCommodity> warehouseCommodityList = new ArrayList<>();
        Optional<Warehouse> warehouseModel = warehouseService.get(warehouse);
        if (warehouseModel.isPresent()) {
            warehouseCommodityList = warehouseCommodityService.getByWarehouse(warehouseModel.get());
        }
        return new ResponseEntity(warehouseCommodityList, HttpStatus.OK);
    }

}
