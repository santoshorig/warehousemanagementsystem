package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardTruckForm;
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
 * <h1>InwardTruck controller</h1>
 * <p>
 * This inward truck controller class provides the CRUD actions for inward trucks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-truck")
public class InwardTruckController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardTruckController.class);

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService domainInwardService;

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
    @RequestMapping(method = RequestMethod.GET, value = {"/index/inward/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("InwardTruck index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InwardTruck> inwardTruckList = new ArrayList<>();
        Optional<Inward> inward = inwardService.get(id);
        if (inward.isPresent()) {
            Inward inwardModel = inward.get();
            inwardTruckList = inwardTruckService.getByInward(inwardModel);
            modelAndView.addObject("inward", inwardModel);
        }
        if (inwardTruckList.size() > 0) {
            modelAndView.addObject("models", inwardTruckList);
            Map<String, Integer> truckBagCountSummaryMap = new HashMap<>();
            Map<String, Double> truckWeightSummaryMap = new HashMap<>();
            for (InwardTruck inwardTruck : inwardTruckList) {
                if (!truckBagCountSummaryMap.containsKey(inwardTruck.getVehicleNumber())) {
                    truckBagCountSummaryMap.put(inwardTruck.getVehicleNumber(), 0);
                }
                Integer bagCount = truckBagCountSummaryMap.get(inwardTruck.getVehicleNumber());
                bagCount += inwardTruck.getTotalBagsBalesDrums();
                truckBagCountSummaryMap.put(inwardTruck.getVehicleNumber(), bagCount);

                if (!truckWeightSummaryMap.containsKey(inwardTruck.getVehicleNumber())) {
                    truckWeightSummaryMap.put(inwardTruck.getVehicleNumber(), 0.0);
                }
                Double weight = 0.0;
                weight += inwardTruck.getGrossWeight();
                truckWeightSummaryMap.put(inwardTruck.getVehicleNumber(), weight);
            }
            modelAndView.addObject("truckBagCountSummary", truckBagCountSummaryMap);
            modelAndView.addObject("truckWeightSummary", truckWeightSummaryMap);
            modelAndView.setViewName("admin/warehouse/inward-truck/index");
        } else {
            modelAndView.setViewName("redirect:/admin/inward-truck/edit/inward/" + id);
        }
        LOGGER.info("InwardTruck index action returned {} models", inwardTruckList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/inward/{inwardId}", "/edit/inward/{inwardId}/{id}"})
    public ModelAndView edit(
            @PathVariable("inwardId") Integer inwardId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("InwardTruck edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardTruckForm model = new InwardTruckForm();
        Optional<Inward> inward = inwardService.get(inwardId);
        List<Weighbridge> weighbridgeList = new ArrayList<>();
        List<QualityLab> qualityLabList = new ArrayList<>();
        Boolean needsLotNumber = false;
        String unitType = "";
        List<Employee> employeeList = new ArrayList<>();
        if (inward.isPresent()) {
            Inward inwardModel = inward.get();
            Set<Commodity> commoditySet = inwardModel.getContract().getCommodity();
            for (Commodity commodity : commoditySet) {
                unitType = commodity.getStorageIn();
            }
            InwardTruck inwardTruckModel = new InwardTruck();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("InwardTruck edit action called on existing model with id {}", identifier);
                Optional<InwardTruck> inwardTruck = inwardTruckService.get(identifier);
                if (inwardTruck.isPresent()) {
                    inwardTruckModel = inwardTruck.get();
                }
            } else {
                inwardTruckModel.setDumpingDate(new Date());
            }
            inwardTruckModel.setInward(inwardModel);
            model.setInwardTruck(inwardTruckModel);
            needsLotNumber = inwardService.needsLotNumber(inwardModel);
            Warehouse warehouse = inwardModel.getContract().getWarehouse();
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

        // Get year values
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> yearList = helperService.getYearList(currentYear, 5);

        List<Supplier> supplierList = supplierService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("unitType", unitType);
        modelAndView.addObject("needsLotNumber", needsLotNumber);
        modelAndView.addObject("suppliers", supplierList);
        modelAndView.addObject("qualityLabs", qualityLabList);
        modelAndView.addObject("weighbridges", weighbridgeList);
        modelAndView.addObject("employees", employeeList);
        modelAndView.addObject("yearList", yearList);
        modelAndView.setViewName("admin/warehouse/inward-truck/edit");
        LOGGER.info("InwardTruck edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardTruckForm inwardTruckForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardTruck save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward-truck/edit");
        if (!result.hasErrors()) {
            try {
                InwardTruck inwardTruck = inwardTruckForm.getInwardTruck();
                InwardTruck existingInwardTruck = new InwardTruck();
                if (inwardTruck.getId() != null) {
                    Optional<InwardTruck> inwardTruckModel = inwardTruckService.get(inwardTruck.getId());
                    if (inwardTruckModel.isPresent()) {
                        existingInwardTruck = inwardTruckModel.get();
                    }
                }
                if (inwardTruckForm.getGatePassDocument().isEmpty()) {
                    if (existingInwardTruck.getId() != null && existingInwardTruck.getGatePassDocument() != null) {
                        inwardTruck.setGatePassDocument(existingInwardTruck.getGatePassDocument());
                    }
                } else {
                    MultipartFile file = inwardTruckForm.getGatePassDocument();
                    Document document = documentService.store(file);
                    inwardTruck.setGatePassDocument(document);
                }

                if (inwardTruckForm.getWeighmentDocument().isEmpty()) {
                    if (existingInwardTruck.getId() != null && existingInwardTruck.getWeighmentDocument() != null) {
                        inwardTruck.setWeighmentDocument(existingInwardTruck.getWeighmentDocument());
                    }
                } else {
                    MultipartFile file = inwardTruckForm.getWeighmentDocument();
                    Document document = documentService.store(file);
                    inwardTruck.setWeighmentDocument(document);
                }

                if (inwardTruckForm.getMandiReceiptDocument().isEmpty()) {
                    if (existingInwardTruck.getId() != null && existingInwardTruck.getMandiReceiptDocument() != null) {
                        inwardTruck.setMandiReceiptDocument(existingInwardTruck.getMandiReceiptDocument());
                    }
                } else {
                    MultipartFile file = inwardTruckForm.getMandiReceiptDocument();
                    Document document = documentService.store(file);
                    inwardTruck.setMandiReceiptDocument(document);
                }

                if (inwardTruckForm.getQualityCheckDocument().isEmpty()) {
                    if (existingInwardTruck.getId() != null && existingInwardTruck.getQualityCheckDocument() != null) {
                        inwardTruck.setQualityCheckDocument(existingInwardTruck.getQualityCheckDocument());
                    }
                } else {
                    MultipartFile file = inwardTruckForm.getQualityCheckDocument();
                    Document document = documentService.store(file);
                    inwardTruck.setQualityCheckDocument(document);
                }

                inwardTruckService.edit(inwardTruck);
                Boolean averageWeightsUpdated = domainInwardService.updateAverageWeights(inwardTruck.getInward());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InwardTruck save action successful");
                modelAndView.setViewName("redirect:/admin/inward-truck/index/inward/" + inwardTruck
                        .getInward()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InwardTruck save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InwardTruck delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer inwardId = null;
        try {
            Optional<InwardTruck> inwardTruck = inwardTruckService.get(id);
            if (inwardTruck.isPresent()) {
                InwardTruck model = inwardTruck.get();
                inwardId = model.getInward().getId();
                inwardTruckService.delete(id);
                LOGGER.info("InwardTruck delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward-truck/index/inward/" + inwardId);
        return modelAndView;
    }

}
