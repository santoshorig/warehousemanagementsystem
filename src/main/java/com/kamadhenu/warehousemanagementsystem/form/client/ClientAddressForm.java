package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Client Address Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientAddressForm {

    @NotNull
    private ClientAddress clientAddress;

    @NotNull
    private boolean sameForAllAddresses;
}
