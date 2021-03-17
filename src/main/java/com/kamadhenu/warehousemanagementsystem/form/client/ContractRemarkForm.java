package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Contract Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractRemarkForm {

    @NotNull
    private String remark;

    @NotNull
    private String status;

    @NotNull
    private Contract contract;
}
