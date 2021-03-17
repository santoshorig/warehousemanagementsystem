package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodity;

import java.util.List;
import java.util.Optional;

/**
 * Contract Commodity interface
 */
public interface ContractCommodityService {

    /**
     * Get contract commodity
     *
     * @param id
     * @return
     */
    Optional<ContractCommodity> get(Integer id);

    /**
     * Edit contract commodity
     *
     * @param contractCommodity
     * @return
     */
    ContractCommodity edit(ContractCommodity contractCommodity);

    /**
     * Delete contract commodity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractCommodity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract commodity
     *
     * @return
     */
    List<ContractCommodity> getAll();

    /**
     * Get contract commodity count
     *
     * @return
     */
    Long count();

    /**
     * Get contract commodity
     *
     * @param contract
     * @return
     */
    List<ContractCommodity> getByContract(Contract contract);
}
