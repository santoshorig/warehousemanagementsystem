package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.SrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.SrRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrServiceImpl implements SrService {

    @Autowired
    private SrRepository srRepository;

    @Autowired
    private HelperService helperService;
    
    @Autowired
    private ConstantService constantService;

    /**
     * Get sr
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Sr> get(Integer id) {
        return srRepository.findById(id);
    }

    /**
     * Edit sr
     *
     * @param sr
     * @return
     */
    @Override
    public Sr edit(Sr sr) {
        return srRepository.save(sr);
    }

    /**
     * Delete sr
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        srRepository.deleteById(id);
    }

    /**
     * Get all sr basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Sr> getAll(Integer pageNumber, Integer pageSize) {
        return srRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all sr
     *
     * @return
     */
    @Override
    public List<Sr> getAll() {
        return srRepository.findAll();
    }

    /**
     * Get sr count
     *
     * @return
     */
    public Long count() {
        return srRepository.count();
    }
    
    /**
     * Check if SR can be edited
     *
     * @param sr
     * @return
     */
    public Boolean canEdit(Sr sr) {
      return sr.getStatus().equalsIgnoreCase(constantService.getSR_STATUS().get("pendingforbusinessreview")) || 
          (sr.getId() == null);
    }

    /**
     * Get sr by status
     *
     * @return
     */
    public List<Sr> getByStatusAndBusinessType() {
        return srRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getSRStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get sr by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Sr> getByStatusAndBusinessType(List<String> statusList) {
        return srRepository
                .findByStatusInAndBusinessTypeInAndBankBranchIsNotNull(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get sr by contract
     *
     * @param contract
     * @return
     */
    public List<Sr> getByContract(Contract contract) {
        return srRepository.findByContract(contract);
    }

    /**
     * Get approved sr by contract
     *
     * @param contract
     * @return
     */
    public List<Sr> getApprovedByContract(Contract contract) {
        return srRepository.findByContractAndStatusIn(contract, helperService.getApprovedSrStatus());
    }

    /**
     * Get sr count by status
     *
     * @return
     */
    public List<SrStatusCount> getCountSrByStatus() {
        return srRepository.countSrByStatus();
    }
}
