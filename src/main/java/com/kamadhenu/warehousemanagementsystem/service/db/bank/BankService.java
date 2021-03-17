package com.kamadhenu.warehousemanagementsystem.service.db.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;

import java.util.List;
import java.util.Optional;

/**
 * bank interface
 */
public interface BankService {

    /**
     * Get bank
     *
     * @param id
     * @return
     */
    Optional<Bank> get(Integer id);

    /**
     * Edit bank
     *
     * @param bank
     * @return
     */
    Bank edit(Bank bank);

    /**
     * Delete bank
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all bank basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Bank> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all bank
     *
     * @return
     */
    List<Bank> getAll();

    /**
     * Get bank count
     *
     * @return
     */
    Long count();
}
