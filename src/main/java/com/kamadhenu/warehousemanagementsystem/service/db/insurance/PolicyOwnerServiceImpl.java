package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyOwner;
import com.kamadhenu.warehousemanagementsystem.repository.insurance.PolicyOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyOwnerServiceImpl implements PolicyOwnerService {

    @Autowired
    private PolicyOwnerRepository policyOwnerRepository;

    /**
     * Get policy owner
     *
     * @param id
     * @return
     */
    @Override
    public Optional<PolicyOwner> get(Integer id) {
        return policyOwnerRepository.findById(id);
    }

    /**
     * Edit policy owner
     *
     * @param policyOwner
     * @return
     */
    @Override
    public PolicyOwner edit(PolicyOwner policyOwner) {
        return policyOwnerRepository.save(policyOwner);
    }

    /**
     * Delete policy owner
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        policyOwnerRepository.deleteById(id);
    }

    /**
     * Get all policy owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<PolicyOwner> getAll(Integer pageNumber, Integer pageSize) {
        return policyOwnerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all policy owner
     *
     * @return
     */
    @Override
    public List<PolicyOwner> getAll() {
        return policyOwnerRepository.findAll();
    }

    /**
     * Get policy owner count
     *
     * @return
     */
    public Long count() {
        return policyOwnerRepository.count();
    }
}
