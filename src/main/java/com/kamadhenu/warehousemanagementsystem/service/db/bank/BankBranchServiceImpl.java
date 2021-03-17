package com.kamadhenu.warehousemanagementsystem.service.db.bank;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import com.kamadhenu.warehousemanagementsystem.repository.bank.BankBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private BankBranchRepository bankBranchRepository;

    /**
     * Get bank branch
     *
     * @param id
     * @return
     */
    @Override
    public Optional<BankBranch> get(Integer id) {
        return bankBranchRepository.findById(id);
    }

    /**
     * Edit bank branch
     *
     * @param bankBranch
     * @return
     */
    @Override
    public BankBranch edit(BankBranch bankBranch) {
        return bankBranchRepository.save(bankBranch);
    }

    /**
     * Delete bank branch
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        bankBranchRepository.deleteById(id);
    }

    /**
     * Get all bank branch basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<BankBranch> getAll(Integer pageNumber, Integer pageSize) {
        return bankBranchRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all bank branch
     *
     * @return
     */
    @Override
    public List<BankBranch> getAll() {
        return bankBranchRepository.findAll();
    }

    /**
     * Get bank branch count
     *
     * @return
     */
    public Long count() {
        return bankBranchRepository.count();
    }

    /**
     * Get all bank branch
     *
     * @param bank
     * @return
     */
    public List<BankBranch> getByBank(Bank bank) {
        return bankBranchRepository.findByBank(bank);
    }
}
