package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Sr Remark Status Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SrRemarkStatusForm {

    @NotNull
    private Sr sr;

    @NotNull
    private Double spotPrice;

    @NotNull
    private String remark;

    @NotNull
    private String status;
}
