package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Outward Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardRemarkForm {

    @NotNull
    private String status;

    @NotNull
    private Outward outward;

    private List<WarehouseStack> warehouseStackList;

    @NotNull
    private String remark;
}
