package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Outward Truck Bag Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardTruckBagForm {

    @NotNull
    private OutwardTruck outwardTruck;

    @NotNull
    private List<DoInwardTruckBag> doInwardTruckBagList;
}
