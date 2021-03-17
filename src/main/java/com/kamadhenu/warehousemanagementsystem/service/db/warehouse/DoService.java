package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;

import java.util.List;
import java.util.Optional;

/**
 * Do interface
 */
public interface DoService {

    /**
     * Get do
     *
     * @param id
     * @return
     */
    Optional<Do> get(Integer id);

    /**
     * Edit do
     *
     * @param doModel
     * @return
     */
    Do edit(Do doModel);

    /**
     * Delete do
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all do basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Do> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all do
     *
     * @return
     */
    List<Do> getAll();

    /**
     * Get do count
     *
     * @return
     */
    Long count();

    /**
     * Get dos by status and business type
     *
     * @return
     */
    List<Do> getByStatusAndBusinessType();

    /**
     * Get dos by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Do> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get do by contract
     *
     * @param contract
     * @return
     */
    List<Do> getByContract(Contract contract);

    /**
     * Get approved do by contract
     *
     * @param contract
     * @return
     */
    List<Do> getApprovedByContract(Contract contract);

    /**
     * Get do for outward
     *
     * @return
     */
    List<Do> getForOutward();
}