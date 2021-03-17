package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Do Bag Type Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DoBagTypeForm {

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
