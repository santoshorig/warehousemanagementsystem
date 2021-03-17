package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Remark repository class
 */
@Repository
public interface ContractRemarkRepository extends JpaRepository<ContractRemark, Integer> {

    /**
     * Get contract remark basis of contract ordered by remark date descending
     *
     * @param contract
     * @return
     */
    List<ContractRemark> findByContractOrderByIdDescRemarkDateDesc(Contract contract);

}
