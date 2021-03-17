package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Lr Warehouse Stack Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LrWarehouseStackForm {

    @NotNull
    private WarehouseStack warehouseStack;

    @NotNull
    private List<LrBagTypeForm> lrBagTypeFormList;

}
