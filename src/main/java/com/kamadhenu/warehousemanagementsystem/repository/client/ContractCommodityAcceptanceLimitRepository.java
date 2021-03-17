package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Commodity Acceptance Limit repository class
 */
@Repository
public interface ContractCommodityAcceptanceLimitRepository extends
        JpaRepository<ContractCommodityAcceptanceLimit, Integer> {

    /**
     * Find by contract
     *
     * @param contract
     * @return
     */
    @Query("SELECT ccal from ContractCommodityAcceptanceLimit ccal left join ContractCommodity cc on ccal.contractCommodity = cc.id WHERE cc.contract = ?1")
    List<ContractCommodityAcceptanceLimit> findByContract(Contract contract);
}
