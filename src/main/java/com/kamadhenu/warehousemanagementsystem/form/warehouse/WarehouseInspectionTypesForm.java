package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Inspection Types Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseInspectionTypesForm {

    @NotNull
    private InspectionType inspectionType;

    @NotNull
    private List<WarehouseInspectionForm> warehouseInspectionForm;

}
