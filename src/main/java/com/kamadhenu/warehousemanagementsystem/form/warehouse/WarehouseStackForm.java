package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Warehouse Stack Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseStackForm {

    @NotNull
    private Warehouse warehouse;

    @NotNull
    private String shed;

    @NotNull
    private String chamber;

    @NotNull
    private String stack;

    private Double length;

    private Double breadth;

    private Double height;
}
