package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ContractStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract repository class
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    /**
     * Get contract by status
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Contract> findByStatusInAndBusinessTypeIn(
            List<String> statusList,
            List<BusinessType> businessType
    );

    /**
     * Get contract by parent contract
     *
     * @param parentContract
     * @return
     */
    List<Contract> findByParentContract(Contract parentContract);

    /**
     * Get contract count by status
     *
     * @return
     */
    @Query("SELECT c.status AS status, COUNT(c.id) AS count FROM Contract c GROUP BY c.status")
    List<ContractStatusCount> countContractByStatus();
}
