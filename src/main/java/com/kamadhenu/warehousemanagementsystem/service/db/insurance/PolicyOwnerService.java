package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyOwner;

import java.util.List;
import java.util.Optional;

/**
 * Policy Owner interface
 */
public interface PolicyOwnerService {

    /**
     * Get policy owner
     *
     * @param id
     * @return
     */
    Optional<PolicyOwner> get(Integer id);

    /**
     * Edit policy owner
     *
     * @param policyOwner
     * @return
     */
    PolicyOwner edit(PolicyOwner policyOwner);

    /**
     * Delete policy owner
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all policy owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<PolicyOwner> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all policy owner
     *
     * @return
     */
    List<PolicyOwner> getAll();

    /**
     * Get policy owner count
     *
     * @return
     */
    Long count();
}
