package com.kamadhenu.warehousemanagementsystem.model.domain.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientBank;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Client Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Client {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.client.Client client;

    @NotNull
    private List<ClientPartner> clientPartner;

    @NotNull
    private List<ClientBank> clientBank;

    @NotNull
    private List<ClientSignatory> clientSignatory;

    @NotNull
    private List<ClientDocument> clientDocument;

    @NotNull
    private String businessAddress;
}
