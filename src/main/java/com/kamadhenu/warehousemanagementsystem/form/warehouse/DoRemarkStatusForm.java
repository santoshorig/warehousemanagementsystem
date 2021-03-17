package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Do Remark Status Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DoRemarkStatusForm {

    @NotNull
    private Do doModel;

    @NotNull
    private String remark;

    @NotNull
    private String status;
}
