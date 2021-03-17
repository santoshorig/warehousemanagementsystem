package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Asset;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseAsset;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.AssetService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseAssetService;
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
 * <h1>WarehouseAsset controller</h1>
 * <p>
 * This warehouse asset controller class provides the CRUD actions for warehouse assets
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-asset")
public class WarehouseAssetController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseAssetController.class);

    @Autowired
    private WarehouseAssetService warehouseAssetService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/warehouse/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseAsset index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseAsset> warehouseAssetList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseAssetList = warehouseAssetService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseAssetList.size() > 0) {
            modelAndView.addObject("models", warehouseAssetList);
            modelAndView.setViewName("admin/warehouse/warehouse-asset/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-asset/edit/warehouse/" + id);
        }
        LOGGER.info("WarehouseAsset index action returned {} models", warehouseAssetList.size());
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
        LOGGER.info("WarehouseAsset edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseAsset model = new WarehouseAsset();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseAsset edit action called on existing model with id {}", identifier);
                Optional<WarehouseAsset> warehouseAsset = warehouseAssetService.get(identifier);
                if (warehouseAsset.isPresent()) {
                    model = warehouseAsset.get();
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
        List<Asset> assetList = assetService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("warehouses", applicableWarehouseList);
        modelAndView.addObject("assetTypes", assetList);
        modelAndView.setViewName("admin/warehouse/warehouse-asset/edit");
        LOGGER.info("WarehouseAsset edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseAsset warehouseAsset,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseAsset save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-asset/edit");
        if (!result.hasErrors()) {
            try {
                warehouseAssetService.edit(warehouseAsset);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseAsset save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-asset/index/warehouse/" + warehouseAsset
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
            LOGGER.error("WarehouseAsset save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseAsset delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseAsset> warehouseAsset = warehouseAssetService.get(id);
            if (warehouseAsset.isPresent()) {
                WarehouseAsset model = warehouseAsset.get();
                warehouseId = model.getWarehouse().getId();
                warehouseAssetService.delete(id);
                LOGGER.info("WarehouseAsset delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-asset/index/warehouse/" + warehouseId);
        return modelAndView;
    }

    /**
     * Get mtf recommendation by asset and warehouse action
     *
     * @param warehouseId the id of warehouse
     * @param assetId     the id of asset
     * @return json
     */
    @RequestMapping(value = "/getMtfRecommendationByWarehouseAndAsset/{warehouseId}/{assetId}", method = RequestMethod.GET)
    public ResponseEntity getMtfRecommendationByWarehouseAndAsset(
            @PathVariable("warehouseId") Integer warehouseId,
            @PathVariable("assetId") Integer assetId
    ) {
        Integer mtfRecommendation = 0;
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            List<WarehouseDetail> warehouseDetailList = warehouseDetailService.getByWarehouse(warehouseModel);
            if (warehouseDetailList.size() > 0) {
                WarehouseDetail warehouseDetail = warehouseDetailList.get(0);
                Optional<Asset> asset = assetService.get(assetId);
                if (asset.isPresent()) {
                    Asset assetModel = asset.get();
                    Double mtf = assetModel.getMtfRecommendation() * warehouseDetail.getArea();
                    mtfRecommendation = Math.round(mtf.intValue());
                }
            }
        }

        return new ResponseEntity(mtfRecommendation, HttpStatus.OK);
    }
}
