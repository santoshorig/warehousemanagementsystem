package com.kamadhenu.warehousemanagementsystem.service.db.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;

import java.util.List;
import java.util.Optional;

/**
 * bank Branch interface
 */
public interface BankBranchService {

    /**
     * Get bank branch
     *
     * @param id
     * @return
     */
    Optional<BankBranch> get(Integer id);

    /**
     * Edit bank branch
     *
     * @param bankBranch
     * @return
     */
    BankBranch edit(BankBranch bankBranch);

    /**
     * Delete bank branch
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all bank branch basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<BankBranch> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all bank branch
     *
     * @return
     */
    List<BankBranch> getAll();

    /**
     * Get bank branch count
     *
     * @return
     */
    Long count();

    /**
     * Get all bank branch
     *
     * @param bank
     * @return
     */
    List<BankBranch> getByBank(Bank bank);
}
