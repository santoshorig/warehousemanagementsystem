package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodity;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractCommodityServiceImpl implements ContractCommodityService {

    @Autowired
    private ContractCommodityRepository contractCommodityRepository;

    /**
     * Get contract commodity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractCommodity> get(Integer id) {
        return contractCommodityRepository.findById(id);
    }

    /**
     * Edit contract commodity
     *
     * @param contractCommodity
     * @return
     */
    @Override
    public ContractCommodity edit(ContractCommodity contractCommodity) {
        return contractCommodityRepository.save(contractCommodity);
    }

    /**
     * Delete contract commodity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractCommodityRepository.deleteById(id);
    }

    /**
     * Get all contract commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractCommodity> getAll(Integer pageNumber, Integer pageSize) {
        return contractCommodityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract commodity
     *
     * @return
     */
    @Override
    public List<ContractCommodity> getAll() {
        return contractCommodityRepository.findAll();
    }

    /**
     * Get contract commodity count
     *
     * @return
     */
    public Long count() {
        return contractCommodityRepository.count();
    }

    /**
     * Get contract commodity
     *
     * @param contract
     * @return
     */
    public List<ContractCommodity> getByContract(Contract contract) {
        return contractCommodityRepository.findByContract(contract);
    }
}
