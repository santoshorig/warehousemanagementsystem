package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;

import java.util.List;
import java.util.Optional;

/**
 * Contract Commodity Acceptance Limit interface
 */
public interface ContractCommodityAcceptanceLimitService {

    /**
     * Get contract commodity acceptance limit
     *
     * @param id
     * @return
     */
    Optional<ContractCommodityAcceptanceLimit> get(Integer id);

    /**
     * Edit contract commodity acceptance limit
     *
     * @param contractCommodityAcceptanceLimit
     * @return
     */
    ContractCommodityAcceptanceLimit edit(ContractCommodityAcceptanceLimit contractCommodityAcceptanceLimit);

    /**
     * Delete contract commodity acceptance limit
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract commodity acceptance limit basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractCommodityAcceptanceLimit> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract commodity acceptance limit
     *
     * @return
     */
    List<ContractCommodityAcceptanceLimit> getAll();

    /**
     * Get contract commodity acceptance limit count
     *
     * @return
     */
    Long count();

    /**
     * Get by contract
     *
     * @param contract
     * @return
     */
    List<ContractCommodityAcceptanceLimit> getByContract(Contract contract);
}
