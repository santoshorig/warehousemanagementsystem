package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Lr Remark Status Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LrRemarkStatusForm {

    @NotNull
    private Lr lr;

    @NotNull
    private String remark;

    @NotNull
    private String status;
}
