package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyType;

import java.util.List;
import java.util.Optional;

/**
 * Policy Type interface
 */
public interface PolicyTypeService {

    /**
     * Get policy type
     *
     * @param id
     * @return
     */
    Optional<PolicyType> get(Integer id);

    /**
     * Edit policy type
     *
     * @param policyType
     * @return
     */
    PolicyType edit(PolicyType policyType);

    /**
     * Delete policy type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all policy type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<PolicyType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all policy type
     *
     * @return
     */
    List<PolicyType> getAll();

    /**
     * Get policy type count
     *
     * @return
     */
    Long count();
}
