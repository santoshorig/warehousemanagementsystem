package com.kamadhenu.warehousemanagementsystem.repository.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * bank repository class
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

}
