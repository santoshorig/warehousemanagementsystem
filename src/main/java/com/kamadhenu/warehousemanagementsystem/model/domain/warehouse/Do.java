package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Do Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Do {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do doModel;

    @NotNull
    private List<DoInwardTruckBag> doInwardTruckBagList;

    @NotNull
    private List<DoRemark> doRemarkList;

    /**
     * Get total weight
     *
     * @return
     */
    public Double getTotalWeight() {
        Double totalWeight = 0.0;
        for (DoInwardTruckBag doInwardTruckBag : doInwardTruckBagList) {
            totalWeight += doInwardTruckBag.getCalculatedWeight();
        }
        return totalWeight;
    }

    /**
     * Get bag type totals
     *
     * @return
     */
    public Map<BagType, Integer> getBagTypeTotals() {
        Map<BagType, Integer> bagTypeMap = new HashMap<>();
        for (DoInwardTruckBag doInwardTruckBag : doInwardTruckBagList) {
            if (!bagTypeMap.containsKey(doInwardTruckBag.getInwardTruckBag().getBagType())) {
                bagTypeMap.put(doInwardTruckBag.getInwardTruckBag().getBagType(), 0);
            }
            Integer countOfBags = bagTypeMap.get(doInwardTruckBag.getInwardTruckBag().getBagType());
            countOfBags++;
            bagTypeMap.replace(doInwardTruckBag.getInwardTruckBag().getBagType(), countOfBags);
        }
        return bagTypeMap;
    }
}
