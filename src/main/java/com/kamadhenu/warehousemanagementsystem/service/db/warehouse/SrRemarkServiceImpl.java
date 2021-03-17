package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.SrRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrRemarkServiceImpl implements SrRemarkService {

    @Autowired
    private SrRemarkRepository srRemarkRepository;

    /**
     * Get sr remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<SrRemark> get(Integer id) {
        return srRemarkRepository.findById(id);
    }

    /**
     * Edit sr remark
     *
     * @param srRemark
     * @return
     */
    @Override
    public SrRemark edit(SrRemark srRemark) {
        return srRemarkRepository.save(srRemark);
    }

    /**
     * Delete sr remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        srRemarkRepository.deleteById(id);
    }

    /**
     * Get all sr remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<SrRemark> getAll(Integer pageNumber, Integer pageSize) {
        return srRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all sr remark
     *
     * @return
     */
    @Override
    public List<SrRemark> getAll() {
        return srRemarkRepository.findAll();
    }

    /**
     * Get sr remark count
     *
     * @return
     */
    public Long count() {
        return srRemarkRepository.count();
    }

    /**
     * Get sr remark
     *
     * @param sr
     * @return
     */
    @Override
    public List<SrRemark> getBySr(Sr sr) {
        return srRemarkRepository.findBySrOrderByIdDescRemarkDateDesc(sr);
    }
}
