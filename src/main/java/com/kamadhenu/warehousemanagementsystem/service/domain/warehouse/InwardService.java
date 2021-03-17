package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Inward domain service
 */
@Service("inwardService")
public class InwardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardService.class);

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService inwardService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private InwardTruckInvoiceService inwardTruckInvoiceService;

    @Autowired
    private InwardTruckQualityCheckService inwardTruckQualityCheckService;

    @Autowired
    private InwardMadeUpBagService inwardMadeUpBagService;

    @Autowired
    private InwardRemarkService inwardRemarkService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private HelperService helperService;

    /**
     * Get domain inward based on database inward
     *
     * @param inwardModel
     * @return
     */
    public Inward get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward inwardModel) {
        Inward inward = new Inward();
        inward.setInward(inwardModel);

        List<com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck> inwardTruckList =
                new ArrayList<>();
        List<WarehouseStack> addedWarehouseStackList = new ArrayList<>();
        List<InwardTruck> existingInwardTruckList = inwardTruckService.getByInward(inwardModel);
        for (InwardTruck inwardTruck : existingInwardTruckList) {
            List<InwardTruckBag> inwardTruckBagList = inwardTruckBagService.getByInwardTruck(inwardTruck);

            // Get for inward
            Map<WarehouseStack, Integer> inwardTruckBagsInStack = new HashMap<>();
            Map<WarehouseStack, Double> inwardTruckWeightInStack = new HashMap<>();
            // Get stack details
            List<WarehouseStack> warehouseStackList = new ArrayList<>();
            for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
                if (!addedWarehouseStackList.contains(inwardTruckBag.getWarehouseStack())) {
                    warehouseStackList.add(inwardTruckBag.getWarehouseStack());
                    addedWarehouseStackList.add(inwardTruckBag.getWarehouseStack());
                }
                if (!inwardTruckBagsInStack.containsKey(inwardTruckBag.getWarehouseStack())) {
                    inwardTruckBagsInStack.put(inwardTruckBag.getWarehouseStack(), 0);
                }
                inwardTruckBagsInStack.replace(
                        inwardTruckBag.getWarehouseStack(),
                        inwardTruckBagsInStack.get(inwardTruckBag.getWarehouseStack()) + 1
                );
                if (!inwardTruckWeightInStack.containsKey(inwardTruckBag.getWarehouseStack())) {
                    inwardTruckWeightInStack.put(inwardTruckBag.getWarehouseStack(), 0.0);
                }
                inwardTruckWeightInStack.replace(
                        inwardTruckBag.getWarehouseStack(),
                        inwardTruckWeightInStack.get(inwardTruckBag.getWarehouseStack()) + inwardTruckBag.getWeight()
                );
            }

            // Get unique warehouse stack
            HashSet<WarehouseStack> warehouseStackHashSet = new HashSet(warehouseStackList);
            Map<WarehouseStack, Integer> totalBagsInStack = new HashMap<>();
            Map<WarehouseStack, Double> totalWeightInStack = new HashMap<>();
            for (WarehouseStack warehouseStack : warehouseStackHashSet) {
                List<InwardTruckBag> stackInwardTruckBagList =
                        inwardTruckBagService.getByWarehouseStack(warehouseStack);
                totalBagsInStack.put(warehouseStack, stackInwardTruckBagList.size());
                Double warehouseStackWeight = new Double(0.0);
                for (InwardTruckBag inwardTruckBag : stackInwardTruckBagList) {
                    warehouseStackWeight += inwardTruckBag.getWeight();
                }
                totalWeightInStack.put(warehouseStack, warehouseStackWeight);
            }

            List<InwardTruckInvoice> inwardTruckInvoiceList = inwardTruckInvoiceService.getByInwardTruck(inwardTruck);
            List<InwardTruckQualityCheck> inwardTruckQualityCheckList =
                    inwardTruckQualityCheckService.getByInwardTruck(inwardTruck
                    );
            com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck inwardTruckModel =
                    new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck();
            inwardTruckModel.setInwardTruck(inwardTruck);
            inwardTruckModel.setInwardTruckBagList(inwardTruckBagList);
            inwardTruckModel.setInwardTruckInvoiceList(inwardTruckInvoiceList);
            inwardTruckModel.setInwardTruckQualityCheckList(inwardTruckQualityCheckList);
            inwardTruckModel.setTotalBagsInStack(totalBagsInStack);
            inwardTruckModel.setTotalWeightInStack(totalWeightInStack);
            inwardTruckModel.setInwardTruckBagsInStack(inwardTruckBagsInStack);
            inwardTruckModel.setInwardTruckWeightInStack(inwardTruckWeightInStack);
            inwardTruckList.add(inwardTruckModel);
        }
        inward.setInwardTruckList(inwardTruckList);

        List<InwardMadeUpBag> inwardMadeUpBagList = inwardMadeUpBagService.getByInward(inwardModel);
        inward.setInwardMadeUpBagList(inwardMadeUpBagList);

        List<InwardRemark> inwardRemarkList = inwardRemarkService.getByInward(inwardModel);
        inward.setInwardRemarkList(inwardRemarkList);

        Optional<RegionLocation> regionLocation =
                regionLocationService.get(inwardModel.getWarehouseCad().getWarehouse().getRegLoc());
        if (regionLocation.isPresent()) {
            Location location = regionLocation.get().getLocation();
            inward.setLocation(location);
        }

        return inward;
    }

    /**
     * Update average weights for inward trucks
     *
     * @param inwardModel
     * @return
     */
    public Boolean updateAverageWeights(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward inwardModel) {
        Boolean updatedAverageWeight = true;
        try {
            Inward inward = get(inwardModel);
            List<InwardTruckBag> inwardTruckBagList = new ArrayList<InwardTruckBag>();
            for (com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck inwardTruck : inward
                    .getInwardTruckList()) {
                Map<BagType, Double> averageWeightPerBagType = inwardTruck.getAverageGrossWeightPerBagType();
                for (InwardTruckBag inwardTruckBag : inwardTruck.getInwardTruckBagList()) {
                    inwardTruckBag
                            .setCalculatedWeight(averageWeightPerBagType.get(inwardTruckBag.getBagType()));
                    inwardTruckBagList.add(inwardTruckBag);
                }
            }
            inwardTruckBagService.editBulk(inwardTruckBagList);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
            updatedAverageWeight = false;
        }
        return updatedAverageWeight;
    }
}
