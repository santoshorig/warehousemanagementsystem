package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Commodity;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDetailService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.CommodityService;
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
 * <h1>WarehouseDetail controller</h1>
 * <p>
 * This warehouse detail controller class provides the CRUD actions for warehouse details
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-detail")
public class WarehouseDetailController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseDetailController.class);

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private CommodityService commodityService;

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
        LOGGER.info("WarehouseDetail index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseDetail> warehouseDetailList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseDetailList = warehouseDetailService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseDetailList.size() > 0) {
            WarehouseDetail warehouseDetail = warehouseDetailList.get(0);
            modelAndView.setViewName("redirect:/admin/warehouse-detail/edit/warehouse/" + id + '/' + warehouseDetail
                    .getId());
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-detail/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseDetail index action returned {} models", warehouseDetailList.size());
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
        LOGGER.info("WarehouseDetail edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseDetail model = new WarehouseDetail();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseDetail edit action called on existing model with id {}", identifier);
                Optional<WarehouseDetail> warehouseDetail = warehouseDetailService.get(identifier);
                if (warehouseDetail.isPresent()) {
                    model = warehouseDetail.get();
                }
            }
            model.setWarehouse(warehouseModel);
        }

        List<Commodity> commodityList = commodityService.getCommodityDetails(model.getWarehouse());
        Map<String, String> licenseMap = constantService.getWAREHOUSE_LICENSE();
        Map<String, String> agreementTypeMap = constantService.getAGREEMENT_TYPE();
        Map<String, String> locationTypeMap = constantService.getWAREHOUSE_LOCATION();
        Map<String, String> shapeMap = constantService.getWAREHOUSE_SHAPE();
        Integer gst = constantService.getGST();
        modelAndView.addObject("model", model);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("licenses", licenseMap);
        modelAndView.addObject("agreementTypes", agreementTypeMap);
        modelAndView.addObject("locationTypes", locationTypeMap);
        modelAndView.addObject("shapes", shapeMap);
        modelAndView.addObject("gst", gst);
        modelAndView.setViewName("admin/warehouse/warehouse-detail/edit");
        LOGGER.info("WarehouseDetail edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseDetail warehouseDetail,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseDetail save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-detail/edit");
        if (!result.hasErrors()) {
            try {
                warehouseDetailService.edit(warehouseDetail);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseDetail save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-weighbridge/index/warehouse/" + warehouseDetail
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
            LOGGER.error("WarehouseDetail save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseDetail delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseDetail> warehouseDetail = warehouseDetailService.get(id);
            if (warehouseDetail.isPresent()) {
                WarehouseDetail model = warehouseDetail.get();
                warehouseId = model.getWarehouse().getId();
                warehouseDetailService.delete(id);
                LOGGER.info("WarehouseDetail delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-detail/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
