package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractAddOnService;

import java.util.List;
import java.util.Optional;

/**
 * Contract Add On Service interface
 */
public interface ContractAddOnServiceService {

    /**
     * Get contract add on service
     *
     * @param id
     * @return
     */
    Optional<ContractAddOnService> get(Integer id);

    /**
     * Edit contract add on service
     *
     * @param contractAddOnService
     * @return
     */
    ContractAddOnService edit(ContractAddOnService contractAddOnService);

    /**
     * Delete contract add on service
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractAddOnService> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract add on service
     *
     * @return
     */
    List<ContractAddOnService> getAll();

    /**
     * Get contract add on service count
     *
     * @return
     */
    Long count();

    /**
     * Get contract add on service
     *
     * @param contract
     * @return
     */
    List<ContractAddOnService> getByContract(Contract contract);
}
