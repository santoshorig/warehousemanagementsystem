package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Inward Made Up Bag Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardMadeUpBagForm {

    @NotNull
    private InwardMadeUpBag inwardMadeUpBag;

    @NotNull
    private Integer totalBags;
}
