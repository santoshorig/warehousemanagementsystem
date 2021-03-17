package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Do Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DoRemarkForm {

    @NotNull
    private String status;

    @NotNull
    private Do doModel;

    @NotNull
    private List<DoInwardForm> doInwardFormList;

    @NotNull
    private String remark;
}
