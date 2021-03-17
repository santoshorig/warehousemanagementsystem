package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityInsurance;

import java.util.List;
import java.util.Optional;

/**
 * Contract Commodity Insurance interface
 */
public interface ContractCommodityInsuranceService {

    /**
     * Get contract commodity insurance
     *
     * @param id
     * @return
     */
    Optional<ContractCommodityInsurance> get(Integer id);

    /**
     * Edit contract commodity insurance
     *
     * @param contractCommodityInsurance
     * @return
     */
    ContractCommodityInsurance edit(ContractCommodityInsurance contractCommodityInsurance);

    /**
     * Delete contract commodity insurance
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract commodity insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractCommodityInsurance> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract commodity insurance
     *
     * @return
     */
    List<ContractCommodityInsurance> getAll();

    /**
     * Get contract commodity insurance count
     *
     * @return
     */
    Long count();

    /**
     * Get contract commodity insurance
     *
     * @param contract
     * @return
     */
    List<ContractCommodityInsurance> getByContract(Contract contract);
}
