package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckBag;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Inward Truck Bag Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardTruckBagForm {

    @NotNull
    private InwardTruckBag inwardTruckBag;

    @NotNull
    private Integer totalBags;
}
