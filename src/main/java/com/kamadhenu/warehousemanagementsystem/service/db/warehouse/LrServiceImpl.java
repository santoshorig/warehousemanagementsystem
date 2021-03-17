package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.LrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.LrRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LrServiceImpl implements LrService {

    @Autowired
    private LrRepository lrRepository;

    @Autowired
    private HelperService helperService;

    /**
     * Get lr
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Lr> get(Integer id) {
        return lrRepository.findById(id);
    }

    /**
     * Edit lr
     *
     * @param lr
     * @return
     */
    @Override
    public Lr edit(Lr lr) {
        return lrRepository.save(lr);
    }

    /**
     * Delete lr
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        lrRepository.deleteById(id);
    }

    /**
     * Get all lr basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Lr> getAll(Integer pageNumber, Integer pageSize) {
        return lrRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all lr
     *
     * @return
     */
    @Override
    public List<Lr> getAll() {
        return lrRepository.findAll();
    }

    /**
     * Get lr count
     *
     * @return
     */
    public Long count() {
        return lrRepository.count();
    }

    /**
     * Get lr by status
     *
     * @return
     */
    public List<Lr> getByStatusAndBusinessType() {
        return lrRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getLRStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get lr by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Lr> getByStatusAndBusinessType(List<String> statusList) {
        return lrRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get lr by sr
     *
     * @param sr
     * @return
     */
    public List<Lr> getBySr(Sr sr) {
        return lrRepository.findBySr(sr);
    }

    /**
     * Get approved lr by sr
     *
     * @param sr
     * @return
     */
    public List<Lr> getApprovedBySr(Sr sr) {
        return lrRepository.findBySrAndStatusIn(sr, helperService.getApprovedLrStatus());
    }

    /**
     * Get lr count by status
     *
     * @return
     */
    public List<LrStatusCount> getCountLrByStatus() {
        return lrRepository.countLrByStatus();
    }
}
