package com.kamadhenu.warehousemanagementsystem.service.domain.client;

import com.kamadhenu.warehousemanagementsystem.model.domain.client.Contract;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Contract domain service
 */
@Service("contractService")
public class ContractService {

    @Autowired
    private ContractAddOnServiceService contractAddOnServiceService;

    @Autowired
    private ContractCommodityService contractCommodityService;

    @Autowired
    private ContractCommodityAcceptanceLimitService contractCommodityAcceptanceLimitService;

    @Autowired
    private ContractCommodityInsuranceService contractCommodityInsuranceService;

    @Autowired
    private ContractDocumentService contractDocumentService;

    /**
     * Get domain contract based on database contract
     *
     * @param contractModel
     * @return
     */
    public Contract get(com.kamadhenu.warehousemanagementsystem.model.db.client.Contract contractModel) {
        Contract contract = new Contract();
        contract.setContract(contractModel);
        contract.setContractAddOnService(contractAddOnServiceService.getByContract(contractModel));
        contract.setContractCommodity(contractCommodityService.getByContract(contractModel));
        contract.setContractCommodityAcceptanceLimit(contractCommodityAcceptanceLimitService
                .getByContract(contractModel));
        contract.setContractCommodityInsurance(contractCommodityInsuranceService.getByContract(contractModel));
        contract.setContractDocument(contractDocumentService.getByContract(contractModel));
        return contract;
    }

}
