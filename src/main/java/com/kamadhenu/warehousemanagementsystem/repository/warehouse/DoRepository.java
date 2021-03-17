package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Do repository class
 */
@Repository
public interface DoRepository extends JpaRepository<Do, Integer> {

    /**
     * Get do by status
     *
     * @param statusList
     * @return
     */
    List<Do> findByStatusIn(List<String> statusList);

    /**
     * Get do by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Do> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Find do by contract
     *
     * @param contract
     * @return
     */
    List<Do> findByContract(Contract contract);

    /**
     * Find do by contract and status
     *
     * @param contract
     * @param statusList
     * @return
     */
    List<Do> findByContractAndStatusIn(Contract contract, List<String> statusList);

    /**
     * Find do for outward
     *
     * @param status
     * @param outward
     * @return
     */
    List<Do> findByStatusAndOutward(String status, Boolean outward);
}
