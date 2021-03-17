package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contract Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractForm {

    private List<Contract> contractList;

    @NotNull
    private boolean lockedInward;

    @NotNull
    private boolean lockedSr;

    @NotNull
    private boolean lockedDo;

    @NotNull
    private boolean lockedOutward;
}
