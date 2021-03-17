package com.kamadhenu.warehousemanagementsystem.repository.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * bank Branch repository class
 */
@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Integer> {

    /**
     * Get branch by bank
     *
     * @param bank
     * @return
     */
    List<BankBranch> findByBank(Bank bank);

}
