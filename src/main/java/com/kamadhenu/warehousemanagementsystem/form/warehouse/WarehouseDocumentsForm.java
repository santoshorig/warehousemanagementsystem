package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Documents Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseDocumentsForm {

    @NotNull
    private List<WarehouseDocumentForm> warehouseDocumentForm;

    @NotNull
    private Warehouse warehouse;
}
