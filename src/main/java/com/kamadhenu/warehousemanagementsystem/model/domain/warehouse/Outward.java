package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardRemark;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Outward Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Outward {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward outward;

    @NotNull
    private List<OutwardTruck> outwardTruckList;

    private List<OutwardMadeUpBag> outwardMadeUpBagList;

    private List<OutwardRemark> outwardRemarkList;

    @NotNull
    private Location location;

    /**
     * Get total bags
     *
     * @return
     */
    public Integer getTotalBags() {
        Integer totalBags = 0;
        for (OutwardTruck outwardTruck : outwardTruckList) {
            totalBags += outwardTruck.getTotalBags();
        }
        return totalBags;
    }

    /**
     * Get total made up bags
     *
     * @return
     */
    public Integer getTotalMadeUpBags() {
        return outwardMadeUpBagList.size();
    }

    /**
     * Get total weight
     *
     * @return
     */
    public Double getTotalWeight() {
        Double weight = 0.0;
        for (OutwardTruck outwardTruck : outwardTruckList) {
            weight += outwardTruck.getTotalWeight();
        }
        return weight;
    }
}
