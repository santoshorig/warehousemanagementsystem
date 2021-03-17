package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inspection;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionOption;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Inspection Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseInspectionForm {

    @NotNull
    private Inspection inspection;

    @NotNull
    private List<InspectionOption> inspectionOption;

    @NotNull
    private WarehouseInspection warehouseInspection;

    private MultipartFile upload;
}
