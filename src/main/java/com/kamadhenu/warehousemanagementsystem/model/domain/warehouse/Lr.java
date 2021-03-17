package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lr Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Lr {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr lr;

    @NotNull
    private List<LrInwardTruckBag> lrInwardTruckBagList;

    @NotNull
    private List<LrRemark> lrRemarkList;

    /**
     * Get bag type totals
     *
     * @return
     */
    public Map<BagType, Integer> getBagTypeTotals() {
        Map<BagType, Integer> bagTypeMap = new HashMap<>();
        for (LrInwardTruckBag lrInwardTruckBag : lrInwardTruckBagList) {
            if (!bagTypeMap.containsKey(lrInwardTruckBag.getInwardTruckBag().getBagType())) {
                bagTypeMap.put(lrInwardTruckBag.getInwardTruckBag().getBagType(), 0);
            }
            Integer countOfBags = bagTypeMap.get(lrInwardTruckBag.getInwardTruckBag().getBagType());
            countOfBags++;
            bagTypeMap.replace(lrInwardTruckBag.getInwardTruckBag().getBagType(), countOfBags);
        }
        return bagTypeMap;
    }

}
