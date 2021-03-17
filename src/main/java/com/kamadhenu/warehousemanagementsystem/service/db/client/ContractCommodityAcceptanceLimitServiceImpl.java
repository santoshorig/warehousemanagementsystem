package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractCommodityAcceptanceLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractCommodityAcceptanceLimitServiceImpl implements ContractCommodityAcceptanceLimitService {

    @Autowired
    private ContractCommodityAcceptanceLimitRepository contractCommodityAcceptanceLimitRepository;

    /**
     * Get contract commodity acceptance limit
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractCommodityAcceptanceLimit> get(Integer id) {
        return contractCommodityAcceptanceLimitRepository.findById(id);
    }

    /**
     * Edit contract commodity acceptance limit
     *
     * @param contractCommodityAcceptanceLimit
     * @return
     */
    @Override
    public ContractCommodityAcceptanceLimit edit(ContractCommodityAcceptanceLimit contractCommodityAcceptanceLimit) {
        return contractCommodityAcceptanceLimitRepository.save(contractCommodityAcceptanceLimit);
    }

    /**
     * Delete contract commodity acceptance limit
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractCommodityAcceptanceLimitRepository.deleteById(id);
    }

    /**
     * Get all contract commodity acceptance limit basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractCommodityAcceptanceLimit> getAll(Integer pageNumber, Integer pageSize) {
        return contractCommodityAcceptanceLimitRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract commodity acceptance limit
     *
     * @return
     */
    @Override
    public List<ContractCommodityAcceptanceLimit> getAll() {
        return contractCommodityAcceptanceLimitRepository.findAll();
    }

    /**
     * Get contract commodity acceptance limit count
     *
     * @return
     */
    public Long count() {
        return contractCommodityAcceptanceLimitRepository.count();
    }

    /**
     * Get by contract
     *
     * @param contract
     * @return
     */
    public List<ContractCommodityAcceptanceLimit> getByContract(Contract contract) {
        return contractCommodityAcceptanceLimitRepository.findByContract(contract);
    }
}
