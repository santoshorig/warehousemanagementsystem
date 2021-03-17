package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardServiceImpl implements OutwardService {

    @Autowired
    private OutwardRepository outwardRepository;

    @Autowired
    private HelperService helperService;

    /**
     * Get outward
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Outward> get(Integer id) {
        return outwardRepository.findById(id);
    }

    /**
     * Edit outward
     *
     * @param outward
     * @return
     */
    @Override
    public Outward edit(Outward outward) {
        return outwardRepository.save(outward);
    }

    /**
     * Delete outward
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardRepository.deleteById(id);
    }

    /**
     * Get all outward basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Outward> getAll(Integer pageNumber, Integer pageSize) {
        return outwardRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward
     *
     * @return
     */
    @Override
    public List<Outward> getAll() {
        return outwardRepository.findAll();
    }

    /**
     * Get outward count
     *
     * @return
     */
    public Long count() {
        return outwardRepository.count();
    }

    /**
     * Get outward by status
     *
     * @return
     */
    public List<Outward> getByStatusAndBusinessType() {
        return outwardRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getOutwardStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get outward by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Outward> getByStatusAndBusinessType(List<String> statusList) {
        return outwardRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get outward by cdf number
     *
     * @param cdfNumber
     * @return
     */
    public List<Outward> getByCdfNumber(String cdfNumber) {
        return outwardRepository.findByCdfNumber(cdfNumber);
    }
}
