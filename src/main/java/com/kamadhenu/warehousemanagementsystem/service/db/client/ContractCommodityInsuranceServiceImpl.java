package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityInsurance;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractCommodityInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractCommodityInsuranceServiceImpl implements ContractCommodityInsuranceService {

    @Autowired
    private ContractCommodityInsuranceRepository contractCommodityInsuranceRepository;

    /**
     * Get contract commodity insurance
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractCommodityInsurance> get(Integer id) {
        return contractCommodityInsuranceRepository.findById(id);
    }

    /**
     * Edit contract commodity insurance
     *
     * @param contractCommodityInsurance
     * @return
     */
    @Override
    public ContractCommodityInsurance edit(ContractCommodityInsurance contractCommodityInsurance) {
        return contractCommodityInsuranceRepository.save(contractCommodityInsurance);
    }

    /**
     * Delete contract commodity insurance
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractCommodityInsuranceRepository.deleteById(id);
    }

    /**
     * Get all contract commodity insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractCommodityInsurance> getAll(Integer pageNumber, Integer pageSize) {
        return contractCommodityInsuranceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract commodity insurance
     *
     * @return
     */
    @Override
    public List<ContractCommodityInsurance> getAll() {
        return contractCommodityInsuranceRepository.findAll();
    }

    /**
     * Get contract commodity insurance count
     *
     * @return
     */
    public Long count() {
        return contractCommodityInsuranceRepository.count();
    }

    /**
     * Get contract commodity insurance
     *
     * @param contract
     * @return
     */
    public List<ContractCommodityInsurance> getByContract(Contract contract) {
        return contractCommodityInsuranceRepository.findByContract(contract);
    }
}
