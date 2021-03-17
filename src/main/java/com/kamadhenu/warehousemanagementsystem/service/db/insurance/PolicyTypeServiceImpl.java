package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyType;
import com.kamadhenu.warehousemanagementsystem.repository.insurance.PolicyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyTypeServiceImpl implements PolicyTypeService {

    @Autowired
    private PolicyTypeRepository policyTypeRepository;

    /**
     * Get policy type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<PolicyType> get(Integer id) {
        return policyTypeRepository.findById(id);
    }

    /**
     * Edit policy type
     *
     * @param policyType
     * @return
     */
    @Override
    public PolicyType edit(PolicyType policyType) {
        return policyTypeRepository.save(policyType);
    }

    /**
     * Delete policy type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        policyTypeRepository.deleteById(id);
    }

    /**
     * Get all policy type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<PolicyType> getAll(Integer pageNumber, Integer pageSize) {
        return policyTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all policy type
     *
     * @return
     */
    @Override
    public List<PolicyType> getAll() {
        return policyTypeRepository.findAll();
    }

    /**
     * Get policy type count
     *
     * @return
     */
    public Long count() {
        return policyTypeRepository.count();
    }
}
