package com.kamadhenu.warehousemanagementsystem.model.domain.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contract Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Contract {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.client.Contract contract;

    @NotNull
    private List<ContractAddOnService> contractAddOnService;

    @NotNull
    private List<ContractCommodity> contractCommodity;

    @NotNull
    private List<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimit;

    @NotNull
    private List<ContractCommodityInsurance> contractCommodityInsurance;

    @NotNull
    private List<ContractDocument> contractDocument;
}
