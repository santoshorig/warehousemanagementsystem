package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardTruckForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

/**
 * <h1>OutwardTruck controller</h1>
 * <p>
 * This outward truck controller class provides the CRUD actions for outward trucks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward-truck")
public class OutwardTruckController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardTruckController.class);

    @Autowired
    private OutwardTruckService outwardTruckService;

    @Autowired
    private OutwardService outwardService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.OutwardService domainOutwardService;

    @Autowired
    private WarehouseWeighbridgeService warehouseWeighbridgeService;

    @Autowired
    private WarehouseCommodityService warehouseCommodityService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/outward/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardTruck index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<OutwardTruck> outwardTruckList = new ArrayList<>();
        Optional<Outward> outward = outwardService.get(id);
        if (outward.isPresent()) {
            Outward outwardModel = outward.get();
            outwardTruckList = outwardTruckService.getByOutward(outwardModel);
            modelAndView.addObject("outward", outwardModel);
        }
        if (outwardTruckList.size() > 0) {
            modelAndView.addObject("models", outwardTruckList);
            Map<String, Integer> truckBagCountSummaryMap = new HashMap<>();
            Map<String, Double> truckWeightSummaryMap = new HashMap<>();
            for (OutwardTruck outwardTruck : outwardTruckList) {
                if (!truckBagCountSummaryMap.containsKey(outwardTruck.getVehicleNumber())) {
                    truckBagCountSummaryMap.put(outwardTruck.getVehicleNumber(), 0);
                }
                Integer bagCount = truckBagCountSummaryMap.get(outwardTruck.getVehicleNumber());
                bagCount += outwardTruck.getTotalBagsBalesDrums();
                truckBagCountSummaryMap.put(outwardTruck.getVehicleNumber(), bagCount);

                if (!truckWeightSummaryMap.containsKey(outwardTruck.getVehicleNumber())) {
                    truckWeightSummaryMap.put(outwardTruck.getVehicleNumber(), 0.0);
                }
                Double weight = 0.0;
                weight += outwardTruck.getGrossWeight();
                truckWeightSummaryMap.put(outwardTruck.getVehicleNumber(), weight);
            }
            modelAndView.addObject("truckBagCountSummary", truckBagCountSummaryMap);
            modelAndView.addObject("truckWeightSummary", truckWeightSummaryMap);
            modelAndView.setViewName("admin/warehouse/outward-truck/index");
        } else {
            modelAndView.setViewName("redirect:/admin/outward-truck/edit/outward/" + id);
        }
        LOGGER.info("OutwardTruck index action returned {} models", outwardTruckList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/outward/{outwardId}", "/edit/outward/{outwardId}/{id}"})
    public ModelAndView edit(
            @PathVariable("outwardId") Integer outwardId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("OutwardTruck edit action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardTruckForm model = new OutwardTruckForm();
        Optional<Outward> outward = outwardService.get(outwardId);
        List<Weighbridge> weighbridgeList = new ArrayList<>();
        List<QualityLab> qualityLabList = new ArrayList<>();
        String unitType = "";
        List<Employee> employeeList = new ArrayList<>();
        if (outward.isPresent()) {
            Outward outwardModel = outward.get();
            Set<Commodity> commoditySet = outwardModel.getDoModel().getContract().getCommodity();
            for (Commodity commodity : commoditySet) {
                unitType = commodity.getStorageIn();
            }
            OutwardTruck outwardTruckModel = new OutwardTruck();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("OutwardTruck edit action called on existing model with id {}", identifier);
                Optional<OutwardTruck> outwardTruck = outwardTruckService.get(identifier);
                if (outwardTruck.isPresent()) {
                    outwardTruckModel = outwardTruck.get();
                }
            } else {
                outwardTruckModel.setLoadingDate(new Date());
            }
            outwardTruckModel.setOutward(outwardModel);
            model.setOutwardTruck(outwardTruckModel);
            Warehouse warehouse = outwardModel.getDoModel().getContract().getWarehouse();
            List<WarehouseWeighbridge> warehouseWeighbridgeList = warehouseWeighbridgeService.getByWarehouse(warehouse);
            for (WarehouseWeighbridge warehouseWeighbridge : warehouseWeighbridgeList) {
                weighbridgeList.add(warehouseWeighbridge.getWeighbridge());
            }
            Integer regionLocationId = warehouse.getRegLoc();
            Optional<RegionLocation> regionLocation = regionLocationService.get(regionLocationId);
            if (regionLocation.isPresent()) {
                employeeList = employeeService.getActiveByLocationAndBusinessType(regionLocation.get().getLocation());
            }

            List<WarehouseCommodity> warehouseCommodityList = warehouseCommodityService.getByWarehouse(warehouse);
            for (WarehouseCommodity warehouseCommodity : warehouseCommodityList) {
                if (!qualityLabList.contains(warehouseCommodity.getQualityLab())) {
                    qualityLabList.add(warehouseCommodity.getQualityLab());
                }
            }
        }
        List<Supplier> supplierList = supplierService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("unitType", unitType);
        modelAndView.addObject("suppliers", supplierList);
        modelAndView.addObject("qualityLabs", qualityLabList);
        modelAndView.addObject("weighbridges", weighbridgeList);
        modelAndView.addObject("employees", employeeList);
        modelAndView.setViewName("admin/warehouse/outward-truck/edit");
        LOGGER.info("OutwardTruck edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardTruckForm outwardTruckForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("OutwardTruck save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/outward-truck/edit");
        if (!result.hasErrors()) {
            try {
                OutwardTruck outwardTruck = outwardTruckForm.getOutwardTruck();
                OutwardTruck existingOutwardTruck = new OutwardTruck();
                if (outwardTruck.getId() != null) {
                    Optional<OutwardTruck> outwardTruckModel = outwardTruckService.get(outwardTruck.getId());
                    if (outwardTruckModel.isPresent()) {
                        existingOutwardTruck = outwardTruckModel.get();
                    }
                }
                if (outwardTruckForm.getGatePassDocument().isEmpty()) {
                    if (existingOutwardTruck.getId() != null && existingOutwardTruck.getGatePassDocument() != null) {
                        outwardTruck.setGatePassDocument(existingOutwardTruck.getGatePassDocument());
                    }
                } else {
                    MultipartFile file = outwardTruckForm.getGatePassDocument();
                    Document document = documentService.store(file);
                    outwardTruck.setGatePassDocument(document);
                }

                if (outwardTruckForm.getWeighmentDocument().isEmpty()) {
                    if (existingOutwardTruck.getId() != null && existingOutwardTruck.getWeighmentDocument() != null) {
                        outwardTruck.setWeighmentDocument(existingOutwardTruck.getWeighmentDocument());
                    }
                } else {
                    MultipartFile file = outwardTruckForm.getWeighmentDocument();
                    Document document = documentService.store(file);
                    outwardTruck.setWeighmentDocument(document);
                }

                if (outwardTruckForm.getQualityCheckDocument().isEmpty()) {
                    if (existingOutwardTruck.getId() != null && existingOutwardTruck
                            .getQualityCheckDocument() != null) {
                        outwardTruck.setQualityCheckDocument(existingOutwardTruck.getQualityCheckDocument());
                    }
                } else {
                    MultipartFile file = outwardTruckForm.getQualityCheckDocument();
                    Document document = documentService.store(file);
                    outwardTruck.setQualityCheckDocument(document);
                }

                outwardTruckService.edit(outwardTruck);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("OutwardTruck save action successful");
                modelAndView.setViewName("redirect:/admin/outward-truck/index/outward/" + outwardTruck
                        .getOutward()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("OutwardTruck save action had errors {}", result.getAllErrors());
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
        LOGGER.info("OutwardTruck delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer outwardId = null;
        try {
            Optional<OutwardTruck> outwardTruck = outwardTruckService.get(id);
            if (outwardTruck.isPresent()) {
                OutwardTruck model = outwardTruck.get();
                outwardId = model.getOutward().getId();
                outwardTruckService.delete(id);
                LOGGER.info("OutwardTruck delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-truck/index/outward/" + outwardId);
        return modelAndView;
    }

}
