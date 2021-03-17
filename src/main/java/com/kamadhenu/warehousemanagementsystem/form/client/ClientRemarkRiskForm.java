package com.kamadhenu.warehousemanagementsystem.form.client;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Client Remark Form
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientRemarkRiskForm extends ClientRemarkForm {

    private List<ClientPartnerForm> clientPartnerFormList;

    @NotNull
    private Boolean fundingEligible;
}
