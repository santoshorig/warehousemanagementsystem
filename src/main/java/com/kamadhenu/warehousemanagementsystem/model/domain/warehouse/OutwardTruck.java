package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Outward Truck Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardTruck {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck outwardTruck;

    @NotNull
    private List<OutwardTruckBag> outwardTruckBagList;

    @NotNull
    private List<OutwardTruckQualityCheck> outwardTruckQualityCheckList;

    /**
     * Get total bags
     *
     * @return
     */
    public Integer getTotalBags() {
        return outwardTruckBagList.size();
    }

    /**
     * Get total weight
     *
     * @return
     */
    public Double getTotalWeight() {
        Double weight = 0.0;
        for (OutwardTruckBag outwardTruckBag : outwardTruckBagList) {
            weight += outwardTruckBag.getDoInwardTruckBag().getCalculatedWeight();
        }
        return weight;
    }

    /**
     * Get qc by group of quality parameter name
     *
     * @return
     */
    public Map<String, Double> getQCGrouped() {
        Map<String, Double> qcMap = new HashMap<>();
        for (OutwardTruckQualityCheck outwardTruckQualityCheck : outwardTruckQualityCheckList) {
            String qualityParameterName = outwardTruckQualityCheck.getQualityParameter().getFriendlyName();
            qcMap.put(qualityParameterName, outwardTruckQualityCheck.getTestResult());
        }
        return qcMap;
    }
}
