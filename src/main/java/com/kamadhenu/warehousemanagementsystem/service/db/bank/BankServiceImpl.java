package com.kamadhenu.warehousemanagementsystem.service.db.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.repository.bank.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    /**
     * Get bank
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Bank> get(Integer id) {
        return bankRepository.findById(id);
    }

    /**
     * Edit bank
     *
     * @param bank
     * @return
     */
    @Override
    public Bank edit(Bank bank) {
        return bankRepository.save(bank);
    }

    /**
     * Delete bank
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        bankRepository.deleteById(id);
    }

    /**
     * Get all bank basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Bank> getAll(Integer pageNumber, Integer pageSize) {
        return bankRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all bank
     *
     * @return
     */
    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    /**
     * Get bank count
     *
     * @return
     */
    public Long count() {
        return bankRepository.count();
    }
}
