package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Manpower;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseManpower;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.ManpowerService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDetailService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseManpowerService;
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
 * <h1>WarehouseManpower controller</h1>
 * <p>
 * This warehouse manpower controller class provides the CRUD actions for warehouse manpowers
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-manpower")
public class WarehouseManpowerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseManpowerController.class);

    @Autowired
    private WarehouseManpowerService warehouseManpowerService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @Autowired
    private ManpowerService manpowerService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/warehouse/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseManpower index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseManpower> warehouseManpowerList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseManpowerList = warehouseManpowerService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseManpowerList.size() > 0) {
            modelAndView.addObject("models", warehouseManpowerList);
            modelAndView.setViewName("admin/warehouse/warehouse-manpower/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-manpower/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseManpower index action returned {} models", warehouseManpowerList.size());
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
        LOGGER.info("WarehouseManpower edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseManpower model = new WarehouseManpower();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseManpower edit action called on existing model with id {}", identifier);
                Optional<WarehouseManpower> warehouseManpower = warehouseManpowerService.get(identifier);
                if (warehouseManpower.isPresent()) {
                    model = warehouseManpower.get();
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
        List<Manpower> manpowerList = manpowerService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("warehouses", applicableWarehouseList);
        modelAndView.addObject("manpowerTypes", manpowerList);
        modelAndView.setViewName("admin/warehouse/warehouse-manpower/edit");
        LOGGER.info("WarehouseManpower edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseManpower warehouseManpower,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseManpower save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-manpower/edit");
        if (!result.hasErrors()) {
            try {
                warehouseManpowerService.edit(warehouseManpower);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseManpower save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-manpower/index/warehouse/" + warehouseManpower
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
            LOGGER.error("WarehouseManpower save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseManpower delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseManpower> warehouseManpower = warehouseManpowerService.get(id);
            if (warehouseManpower.isPresent()) {
                WarehouseManpower model = warehouseManpower.get();
                warehouseId = model.getWarehouse().getId();
                warehouseManpowerService.delete(id);
                LOGGER.info("WarehouseManpower delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-manpower/index/warehouse/" + warehouseId);
        return modelAndView;
    }

    /**
     * Get mtf recommendation by manpower and warehouse action
     *
     * @param warehouseId the id of warehouse
     * @param manpowerId  the id of manpower
     * @return json
     */
    @RequestMapping(value = "/getMtfRecommendationByWarehouseAndManpower/{warehouseId}/{manpowerId}", method = RequestMethod.GET)
    public ResponseEntity getMtfRecommendationByWarehouseAndManpower(
            @PathVariable("warehouseId") Integer warehouseId,
            @PathVariable("manpowerId") Integer manpowerId
    ) {
        Integer mtfRecommendation = 0;
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            List<WarehouseDetail> warehouseDetailList = warehouseDetailService.getByWarehouse(warehouseModel);
            if (warehouseDetailList.size() > 0) {
                WarehouseDetail warehouseDetail = warehouseDetailList.get(0);
                Optional<Manpower> manpower = manpowerService.get(manpowerId);
                if (manpower.isPresent()) {
                    Manpower manpowerModel = manpower.get();
                    Double mtf = manpowerModel.getMtfRecommendation() * warehouseDetail.getArea();
                    mtfRecommendation = Math.round(mtf.intValue());
                }
            }
        }

        return new ResponseEntity(mtfRecommendation, HttpStatus.OK);
    }
}
