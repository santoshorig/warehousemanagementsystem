package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.DoRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoServiceImpl implements DoService {

    @Autowired
    private DoRepository doRepository;

    @Autowired
    private HelperService helperService;

    @Autowired
    private ConstantService constantService;

    /**
     * Get do
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Do> get(Integer id) {
        return doRepository.findById(id);
    }

    /**
     * Edit do
     *
     * @param doModel
     * @return
     */
    @Override
    public Do edit(Do doModel) {
        return doRepository.save(doModel);
    }

    /**
     * Delete do
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        doRepository.deleteById(id);
    }

    /**
     * Get all do basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Do> getAll(Integer pageNumber, Integer pageSize) {
        return doRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all do
     *
     * @return
     */
    @Override
    public List<Do> getAll() {
        return doRepository.findAll();
    }

    /**
     * Get do count
     *
     * @return
     */
    public Long count() {
        return doRepository.count();
    }

    /**
     * Get do by status
     *
     * @return
     */
    public List<Do> getByStatusAndBusinessType() {
        return doRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getDOStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get do by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Do> getByStatusAndBusinessType(List<String> statusList) {
        return doRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get do by contract
     *
     * @param contract
     * @return
     */
    public List<Do> getByContract(Contract contract) {
        return doRepository.findByContract(contract);
    }

    /**
     * Get approved do by contract
     *
     * @param contract
     * @return
     */
    public List<Do> getApprovedByContract(Contract contract) {
        return doRepository.findByContractAndStatusIn(contract, helperService.getApprovedDoStatus());
    }

    /**
     * Get do for outward
     *
     * @return
     */
    public List<Do> getForOutward() {
        return doRepository.findByStatusAndOutward(constantService.getDO_STATUS().get("approved"), false);
    }
}
