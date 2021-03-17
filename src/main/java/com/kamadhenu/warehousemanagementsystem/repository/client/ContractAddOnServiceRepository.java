package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractAddOnService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Add On Service repository class
 */
@Repository
public interface ContractAddOnServiceRepository extends JpaRepository<ContractAddOnService, Integer> {

    /**
     * Get contract add on service by contract
     *
     * @param contract
     * @return
     */
    List<ContractAddOnService> findByContract(Contract contract);
}
