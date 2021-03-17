package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Client Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientRemarkForm {

    @NotNull
    private String remark;

    @NotNull
    private String status;

    @NotNull
    private Client client;
}
