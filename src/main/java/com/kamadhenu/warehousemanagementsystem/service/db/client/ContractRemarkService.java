package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;

import java.util.List;
import java.util.Optional;

/**
 * Contract Remark interface
 */
public interface ContractRemarkService {
    /**
     * Get contractRemark
     *
     * @param id
     * @return
     */
    Optional<ContractRemark> get(Integer id);

    /**
     * Edit client remark
     *
     * @param contractRemark
     * @return
     */
    ContractRemark edit(ContractRemark contractRemark);

    /**
     * Delete client remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client remark remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client remark remark
     *
     * @return
     */
    List<ContractRemark> getAll();

    /**
     * Get client remark remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all contract remark remark basis contract
     *
     * @param contract
     * @return
     */
    List<ContractRemark> getByContract(Contract contract);
}
