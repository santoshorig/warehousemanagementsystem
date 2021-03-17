package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Commodity repository class
 */
@Repository
public interface ContractCommodityRepository extends JpaRepository<ContractCommodity, Integer> {

    /**
     * Get by contract
     *
     * @param contract
     * @return
     */
    List<ContractCommodity> findByContract(Contract contract);
}
