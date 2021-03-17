package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Commodity Insurance repository class
 */
@Repository
public interface ContractCommodityInsuranceRepository extends JpaRepository<ContractCommodityInsurance, Integer> {

    /**
     * Find by contract
     *
     * @param contract
     * @return
     */
    @Query("SELECT cci from ContractCommodityInsurance cci left join ContractCommodity cc on cci.contractCommodity = cc.id WHERE cc.contract = ?1")
    List<ContractCommodityInsurance> findByContract(Contract contract);

}
