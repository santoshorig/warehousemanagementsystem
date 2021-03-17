package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Warehouse Cad Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseCadForm {

    @NotNull
    private Warehouse warehouse;

    @NotNull
    @Min(value = 1)
    private Integer bookNumber;

    @NotNull
    @Min(value = 1)
    private Integer startSerialNumber;

    @NotNull
    @Min(value = 1)
    private Integer endSerialNumber;
}
