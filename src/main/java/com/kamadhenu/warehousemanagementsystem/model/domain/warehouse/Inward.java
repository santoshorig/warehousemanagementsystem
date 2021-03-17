package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardRemark;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inward Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Inward {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward inward;

    @NotNull
    private List<InwardTruck> inwardTruckList;

    private List<InwardMadeUpBag> inwardMadeUpBagList;

    private List<InwardRemark> inwardRemarkList;

    @NotNull
    private Location location;

    /**
     * Get total bags
     *
     * @return
     */
    public Integer getTotalBags() {
        Integer totalBags = 0;
        for (InwardTruck inwardTruck : inwardTruckList) {
            totalBags += inwardTruck.getTotalBags();
        }
        return totalBags;
    }

    /**
     * Get total net weight
     *
     * @return
     */
    public Double getTotalNetWeight() {
        Double totalNetWeight = 0.0;
        for (InwardTruck inwardTruck : inwardTruckList) {
            totalNetWeight += inwardTruck.getTotalNetWeight();
        }
        return totalNetWeight;
    }

    /**
     * Get total net weight in MT
     *
     * @return
     */
    public Double getTotalNetWeightMt() {
        Double totalNetWeight = getTotalNetWeight();
        BigDecimal bd = new BigDecimal(getTotalNetWeight() / 1000).setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Get total made up bags
     *
     * @return
     */
    public Integer getTotalMadeUpBags() {
        return inwardMadeUpBagList.size();
    }

    /**
     * Get made up bags by warehouse stack
     *
     * @return
     */
    public Map<WarehouseStack, Integer> getMadeUpBagsByWarehouseStack() {
        Map<WarehouseStack, Integer> warehouseStackMap = new HashMap<>();
        for (InwardMadeUpBag inwardMadeUpBag : inwardMadeUpBagList) {
            if (!warehouseStackMap.containsKey(inwardMadeUpBag.getWarehouseStack())) {
                warehouseStackMap.put(inwardMadeUpBag.getWarehouseStack(), 0);
            }
            warehouseStackMap.put(
                    inwardMadeUpBag.getWarehouseStack(),
                    warehouseStackMap.get(inwardMadeUpBag.getWarehouseStack()) + 1
            );
        }
        return warehouseStackMap;
    }
}
