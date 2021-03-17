package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contract Documents Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractDocumentsForm {

    @NotNull
    private List<ContractDocumentForm> contractDocumentForm;

    @NotNull
    private Contract contract;

    @NotNull
    private Boolean review;

    @NotNull
    private String remark;
}
