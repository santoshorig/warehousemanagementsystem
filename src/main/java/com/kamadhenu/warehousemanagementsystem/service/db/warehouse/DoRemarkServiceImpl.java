package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.DoRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoRemarkServiceImpl implements DoRemarkService {

    @Autowired
    private DoRemarkRepository doRemarkRepository;

    /**
     * Get do remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<DoRemark> get(Integer id) {
        return doRemarkRepository.findById(id);
    }

    /**
     * Edit do remark
     *
     * @param doRemark
     * @return
     */
    @Override
    public DoRemark edit(DoRemark doRemark) {
        return doRemarkRepository.save(doRemark);
    }

    /**
     * Delete do remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        doRemarkRepository.deleteById(id);
    }

    /**
     * Get all do remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<DoRemark> getAll(Integer pageNumber, Integer pageSize) {
        return doRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all do remark
     *
     * @return
     */
    @Override
    public List<DoRemark> getAll() {
        return doRemarkRepository.findAll();
    }

    /**
     * Get do remark count
     *
     * @return
     */
    public Long count() {
        return doRemarkRepository.count();
    }

    /**
     * Get do remark
     *
     * @param doModel
     * @return
     */
    @Override
    public List<DoRemark> getByDo(Do doModel) {
        return doRemarkRepository.findByDoModelOrderByIdDescRemarkDateDesc(doModel);
    }
}
