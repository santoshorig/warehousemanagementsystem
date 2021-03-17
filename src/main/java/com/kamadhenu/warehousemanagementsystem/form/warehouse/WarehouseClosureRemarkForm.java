package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Warehouse Closure Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseClosureRemarkForm {

    @NotNull
    private String status;

    @NotNull
    private WarehouseClosure warehouseClosure;

    @NotNull
    private String remark;
}
