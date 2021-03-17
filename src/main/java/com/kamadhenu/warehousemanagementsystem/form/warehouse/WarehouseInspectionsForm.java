package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Inspections Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseInspectionsForm {

    @NotNull
    private Boolean review;

    @NotNull
    private List<WarehouseInspectionTypesForm> warehouseInspectionTypesForm;

    @NotNull
    private Warehouse warehouse;

    @NotNull
    private String remark;
}
