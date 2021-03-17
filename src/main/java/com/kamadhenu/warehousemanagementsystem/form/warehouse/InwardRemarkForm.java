package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Inward Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardRemarkForm {

    @NotNull
    private String status;

    @NotNull
    private Inward inward;

    private List<WarehouseStack> warehouseStackList;

    @NotNull
    private String remark;
}
