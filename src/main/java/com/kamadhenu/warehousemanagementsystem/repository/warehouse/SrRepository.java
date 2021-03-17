package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.SrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sr repository class
 */
@Repository
public interface SrRepository extends JpaRepository<Sr, Integer> {

    /**
     * Get sr by status
     *
     * @param statusList
     * @return
     */
    List<Sr> findByStatusIn(List<String> statusList);

    /**
     * Get sr by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Sr> findByStatusInAndBusinessTypeIn(
            List<String> statusList,
            List<BusinessType> businessType
    );

    /**
     * Get sr by status and business type and bank branch not null
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Sr> findByStatusInAndBusinessTypeInAndBankBranchIsNotNull(
            List<String> statusList,
            List<BusinessType> businessType
    );

    /**
     * Find sr by contract
     *
     * @param contract
     * @return
     */
    List<Sr> findByContract(Contract contract);

    /**
     * Find sr by contract and status
     *
     * @param contract
     * @param statusList
     * @return
     */
    List<Sr> findByContractAndStatusIn(Contract contract, List<String> statusList);

    /**
     * Get sr count by status
     *
     * @return
     */
    @Query("SELECT s.status AS status, COUNT(s.id) AS count FROM Sr s GROUP BY s.status")
    List<SrStatusCount> countSrByStatus();
}
