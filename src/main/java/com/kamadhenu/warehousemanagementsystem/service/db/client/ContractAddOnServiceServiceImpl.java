package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractAddOnService;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractAddOnServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractAddOnServiceServiceImpl implements ContractAddOnServiceService {

    @Autowired
    private ContractAddOnServiceRepository contractAddOnServiceRepository;

    /**
     * Get contract add on service
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractAddOnService> get(Integer id) {
        return contractAddOnServiceRepository.findById(id);
    }

    /**
     * Edit contract add on service
     *
     * @param contractAddOnService
     * @return
     */
    @Override
    public ContractAddOnService edit(ContractAddOnService contractAddOnService) {
        return contractAddOnServiceRepository.save(contractAddOnService);
    }

    /**
     * Delete contract add on service
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractAddOnServiceRepository.deleteById(id);
    }

    /**
     * Get all contract add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractAddOnService> getAll(Integer pageNumber, Integer pageSize) {
        return contractAddOnServiceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract add on service
     *
     * @return
     */
    @Override
    public List<ContractAddOnService> getAll() {
        return contractAddOnServiceRepository.findAll();
    }

    /**
     * Get contract add on service count
     *
     * @return
     */
    public Long count() {
        return contractAddOnServiceRepository.count();
    }

    /**
     * Get contract add on service
     *
     * @param contract
     * @return
     */
    public List<ContractAddOnService> getByContract(Contract contract) {
        return contractAddOnServiceRepository.findByContract(contract);
    }
}
