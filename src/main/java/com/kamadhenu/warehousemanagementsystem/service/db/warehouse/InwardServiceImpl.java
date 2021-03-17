package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.InwardStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InwardServiceImpl implements InwardService {

    @Autowired
    private InwardRepository inwardRepository;

    @Autowired
    private HelperService helperService;

    /**
     * Get inward
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Inward> get(Integer id) {
        return inwardRepository.findById(id);
    }

    /**
     * Edit inward
     *
     * @param inward
     * @return
     */
    @Override
    public Inward edit(Inward inward) {
        return inwardRepository.save(inward);
    }

    /**
     * Delete inward
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardRepository.deleteById(id);
    }

    /**
     * Get all inward basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Inward> getAll(Integer pageNumber, Integer pageSize) {
        return inwardRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward
     *
     * @return
     */
    @Override
    public List<Inward> getAll() {
        return inwardRepository.findAll();
    }

    /**
     * Get inward count
     *
     * @return
     */
    public Long count() {
        return inwardRepository.count();
    }

    /**
     * Get inward by status
     *
     * @return
     */
    public List<Inward> getByStatusAndBusinessType() {
        return inwardRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getInwardStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get inward by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Inward> getByStatusAndBusinessType(List<String> statusList) {
        return inwardRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Needs lot number
     *
     * @param inward
     * @return
     */
    public Boolean needsLotNumber(Inward inward) {
        Boolean needsLotNumber = false;
        if (helperService.isGovernmentUser()) {
            Set<Commodity> commoditySet = inward.getContract().getCommodity();
            for (Commodity commodity : commoditySet) {
                if (commodity.getNeedsLotNumber()) {
                    needsLotNumber = true;
                }
            }
        }
        return needsLotNumber;
    }

    /**
     * Get approved inwards by contract
     *
     * @param contract
     * @return
     */
    public List<Inward> getApprovedByContract(Contract contract) {
        return inwardRepository.findByContractAndStatusIn(contract, helperService.getApprovedInwardStatus());
    }

    /**
     * Get open inwards
     *
     * @return
     */
    public List<Inward> getOpen() {
        return inwardRepository.findByStatusIn(helperService.getOpenInwardStatus());
    }

    /**
     * Get inward count by status
     *
     * @return
     */
    public List<InwardStatusCount> getCountInwardByStatus() {
        return inwardRepository.countInwardByStatus();
    }

    /**
     * Get inward by cdd number
     *
     * @param cddNumber
     * @return
     */
    public List<Inward> getByCddNumber(String cddNumber) {
        return inwardRepository.findByCddNumber(cddNumber);
    }
}
