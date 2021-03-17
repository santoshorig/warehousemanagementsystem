package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Client Documents Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientDocumentsForm {

    @NotNull
    private List<ClientDocumentForm> clientDocumentForm;

    @NotNull
    private Client client;

    @NotNull
    private Boolean review;

    @NotNull
    private String remark;
}
