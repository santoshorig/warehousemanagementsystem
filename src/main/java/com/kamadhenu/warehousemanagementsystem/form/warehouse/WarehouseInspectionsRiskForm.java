package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Inspections Risk Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseInspectionsRiskForm {

    @NotNull
    private String status;

    @NotNull
    private List<WarehouseInspectionTypesRiskForm> warehouseInspectionTypesRiskForms;

    @NotNull
    private Warehouse warehouse;

    @NotNull
    private String remark;

    @NotNull
    private MultipartFile upload;
}
