package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ContractStatusCount;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Contract interface
 */
public interface ContractService {

    /**
     * Get contract
     *
     * @param id
     * @return
     */
    Optional<Contract> get(Integer id);

    /**
     * Edit contract
     *
     * @param contract
     * @return
     */
    Contract edit(Contract contract);

    /**
     * Delete contract
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Contract> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract
     *
     * @return
     */
    List<Contract> getAll();

    /**
     * Get contract count
     *
     * @return
     */
    Long count();

    /**
     * Get contracts by status and business type
     *
     * @return
     */
    List<Contract> getByStatusAndBusinessType();

    /**
     * Get contracts by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Contract> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get contracts by parent contract
     *
     * @param parentContract
     * @return
     */
    List<Contract> getByParentContract(Contract parentContract);

    /**
     * Get contracts which are approved
     *
     * @return
     */
    List<Contract> getApprovedContract();

    /**
     * Get contract count by status
     *
     * @return
     */
    List<ContractStatusCount> getCountContractByStatus();

    /**
     * Get spot price by contract
     *
     * @param contract
     * @return
     */
    Double getSpotPriceByContract(Contract contract, Date srDate);
}
