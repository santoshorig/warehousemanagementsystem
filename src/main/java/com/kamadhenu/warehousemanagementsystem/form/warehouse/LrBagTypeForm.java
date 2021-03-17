package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Lr Bag Type Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LrBagTypeForm {

    @NotNull
    private BagType bagType;

    @NotNull
    private Integer total;

    @NotNull
    private Double averageGrossWeight;

    @NotNull
    private Double neededWeight;

    @NotNull
    private Integer neededBags;

}
