package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inward Truck Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardTruck {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck inwardTruck;

    @NotNull
    private List<InwardTruckBag> inwardTruckBagList;

    @NotNull
    private List<InwardTruckInvoice> inwardTruckInvoiceList;

    @NotNull
    private List<InwardTruckQualityCheck> inwardTruckQualityCheckList;

    @NotNull
    private Map<WarehouseStack, Integer> totalBagsInStack;

    @NotNull
    private Map<WarehouseStack, Double> totalWeightInStack;

    @NotNull
    private Map<WarehouseStack, Integer> inwardTruckBagsInStack;

    @NotNull
    private Map<WarehouseStack, Double> inwardTruckWeightInStack;

    /**
     * Get total bags
     *
     * @return
     */
    public Integer getTotalBags() {
        return inwardTruckBagList.size();
    }

    /**
     * Get total net weight
     *
     * @return
     */
    public Double getTotalNetWeight() {
        Double totalNetWeight = 0.0;
        for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
            totalNetWeight += inwardTruckBag.getWeight();
        }
        return totalNetWeight;
    }

    /**
     * Get total gross weight
     *
     * @return
     */
    public Double getTotalGrossWeight() {
        Double totalGrossWeight = 0.0;
        for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
            if (inwardTruckBag.getInwardTruck().hasWeighbridge()) {
                totalGrossWeight += inwardTruckBag.getBagType().getCapacity();
            } else {
                totalGrossWeight += inwardTruckBag.getManualWeight();
            }
        }
        return totalGrossWeight;
    }

    /**
     * Get bags by group by bag type
     *
     * @return
     */
    public Map<BagType, Integer> getBagsGroupedByBagType() {
        Map<BagType, Integer> bagTypeMap = new HashMap<>();
        for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
            if (!bagTypeMap.containsKey(inwardTruckBag.getBagType())) {
                bagTypeMap.put(inwardTruckBag.getBagType(), 0);
            }
            Integer countOfBags = bagTypeMap.get(inwardTruckBag.getBagType());
            countOfBags++;
            bagTypeMap.replace(inwardTruckBag.getBagType(), countOfBags);
        }
        return bagTypeMap;
    }

    /**
     * Get bags by group of friendly name
     *
     * @return
     */
    public Map<String, Integer> getBagsGrouped() {
        Map<String, Integer> bagMap = new HashMap<>();
        Map<BagType, Integer> bagTypeMap = getBagsGroupedByBagType();
        for (Map.Entry<BagType, Integer> bagTypeEntry : bagTypeMap.entrySet()) {
            bagMap.put(bagTypeEntry.getKey().getFriendlyName(), bagTypeEntry.getValue());
        }
        return bagMap;
    }

    /**
     * Get qc by group of quality parameter name
     *
     * @return
     */
    public Map<String, Double> getQCGrouped() {
        Map<String, Double> qcMap = new HashMap<>();
        for (InwardTruckQualityCheck inwardTruckQualityCheck : inwardTruckQualityCheckList) {
            String qualityParameterName = inwardTruckQualityCheck.getQualityParameter().getFriendlyName();
            qualityParameterName +=
                    " ( " + inwardTruckQualityCheck.getMinLimit() + " - " + inwardTruckQualityCheck
                            .getMaxLimit() + " ) ";
            qcMap.put(qualityParameterName, inwardTruckQualityCheck.getTestResult());
        }
        return qcMap;
    }

    /**
     * Average gross weight per bag type
     *
     * @return
     */
    public Map<BagType, Double> getAverageGrossWeightPerBagType() {
        Map<BagType, Double> bagWeightMap = new HashMap<>();
        if (inwardTruck.hasWeighbridge()) {
            Double totalGrossWeight = inwardTruck.getGrossWeight();
            Double totalTareWeight = inwardTruck.getTareWeight();

            Map<BagType, Integer> bagTypeMap = getBagsGroupedByBagType();
            for (Map.Entry<BagType, Integer> bagTypeEntry : bagTypeMap.entrySet()) {
                BagType bagType = bagTypeEntry.getKey();
                Integer totalBags = bagTypeEntry.getValue();
                Double totalWeightForBagType =
                        (totalBags * bagType
                                .getCapacity()) * (1000 * (totalGrossWeight - totalTareWeight)) / getTotalGrossWeight();
                Double averageGrossWeightPerBagType = totalWeightForBagType / totalBags;
                BigDecimal bd = new BigDecimal(averageGrossWeightPerBagType).setScale(3, RoundingMode.HALF_UP);
                bagWeightMap.put(bagType, bd.doubleValue());
            }
        } else {
            Map<BagType, Integer> countManualBagMap = new HashMap<>();
            Map<BagType, Double> weightManualBagMap = new HashMap<>();
            // if manual weight assume whatever bag weight is entered is correct
            for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
                BigDecimal bd = new BigDecimal(inwardTruckBag.getManualWeight()).setScale(3, RoundingMode.HALF_UP);
                if (!weightManualBagMap.containsKey(inwardTruckBag.getBagType())) {
                    countManualBagMap.put(inwardTruckBag.getBagType(), 1);
                    weightManualBagMap.put(inwardTruckBag.getBagType(), bd.doubleValue());
                } else {
                    weightManualBagMap.put(
                            inwardTruckBag.getBagType(),
                            bd.doubleValue() + weightManualBagMap.get(inwardTruckBag.getBagType())
                    );
                    countManualBagMap
                            .put(inwardTruckBag.getBagType(), 1 + countManualBagMap.get(inwardTruckBag.getBagType()));
                }
            }
            for (Map.Entry<BagType, Double> bagTypeEntry : weightManualBagMap.entrySet()) {
                BigDecimal bd = new BigDecimal(bagTypeEntry.getValue() / countManualBagMap.get(bagTypeEntry.getKey()))
                        .setScale(3, RoundingMode.HALF_UP);
                bagWeightMap.put(bagTypeEntry.getKey(), bd.doubleValue());
            }
        }
        return bagWeightMap;
    }
}
