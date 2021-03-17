package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.LrRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LrRemarkServiceImpl implements LrRemarkService {

    @Autowired
    private LrRemarkRepository lrRemarkRepository;

    /**
     * Get lr remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<LrRemark> get(Integer id) {
        return lrRemarkRepository.findById(id);
    }

    /**
     * Edit lr remark
     *
     * @param lrRemark
     * @return
     */
    @Override
    public LrRemark edit(LrRemark lrRemark) {
        return lrRemarkRepository.save(lrRemark);
    }

    /**
     * Delete lr remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        lrRemarkRepository.deleteById(id);
    }

    /**
     * Get all lr remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<LrRemark> getAll(Integer pageNumber, Integer pageSize) {
        return lrRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all lr remark
     *
     * @return
     */
    @Override
    public List<LrRemark> getAll() {
        return lrRemarkRepository.findAll();
    }

    /**
     * Get lr remark count
     *
     * @return
     */
    public Long count() {
        return lrRemarkRepository.count();
    }

    /**
     * Get lr remark
     *
     * @param lr
     * @return
     */
    @Override
    public List<LrRemark> getByLr(Lr lr) {
        return lrRemarkRepository.findByLrOrderByIdDescRemarkDateDesc(lr);
    }
}
