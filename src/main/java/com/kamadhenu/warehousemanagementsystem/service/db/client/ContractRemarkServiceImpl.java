package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractRemarkServiceImpl implements ContractRemarkService {

    @Autowired
    private ContractRemarkRepository contractRemarkRepository;

    /**
     * Get contract remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractRemark> get(Integer id) {
        return contractRemarkRepository.findById(id);
    }

    /**
     * Edit contract remark
     *
     * @param ContractRemark
     * @return
     */
    @Override
    public ContractRemark edit(ContractRemark ContractRemark) {
        return contractRemarkRepository.save(ContractRemark);
    }

    /**
     * Delete contract remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractRemarkRepository.deleteById(id);
    }

    /**
     * Get all contract remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractRemark> getAll(Integer pageNumber, Integer pageSize) {
        return contractRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract remark
     *
     * @return
     */
    @Override
    public List<ContractRemark> getAll() {
        return contractRemarkRepository.findAll();
    }

    /**
     * Get contract remark count
     *
     * @return
     */
    public Long count() {
        return contractRemarkRepository.count();
    }

    /**
     * Get contract remark
     *
     * @param contract
     * @return
     */
    @Override
    public List<ContractRemark> getByContract(Contract contract) {
        return contractRemarkRepository.findByContractOrderByIdDescRemarkDateDesc(contract);
    }
}
