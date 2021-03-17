package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Inspection Types Risk Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseInspectionTypesRiskForm {

    @NotNull
    private InspectionType inspectionType;

    @NotNull
    private List<WarehouseInspection> warehouseInspections;

}
