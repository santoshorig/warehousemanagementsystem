package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Warehouse Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseRemarkForm {

    @NotNull
    private String status;

    @NotNull
    private Warehouse warehouse;

    @NotNull
    private String remark;
}
