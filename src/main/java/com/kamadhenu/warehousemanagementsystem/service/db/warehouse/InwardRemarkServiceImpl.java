package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InwardRemarkServiceImpl implements InwardRemarkService {

    @Autowired
    private InwardRemarkRepository inwardRemarkRepository;

    /**
     * Get inward remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardRemark> get(Integer id) {
        return inwardRemarkRepository.findById(id);
    }

    /**
     * Edit inward remark
     *
     * @param InwardRemark
     * @return
     */
    @Override
    public InwardRemark edit(InwardRemark InwardRemark) {
        return inwardRemarkRepository.save(InwardRemark);
    }

    /**
     * Delete inward remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardRemarkRepository.deleteById(id);
    }

    /**
     * Get all inward remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardRemark> getAll(Integer pageNumber, Integer pageSize) {
        return inwardRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward remark
     *
     * @return
     */
    @Override
    public List<InwardRemark> getAll() {
        return inwardRemarkRepository.findAll();
    }

    /**
     * Get inward remark count
     *
     * @return
     */
    public Long count() {
        return inwardRemarkRepository.count();
    }

    /**
     * Get inward remark
     *
     * @param inward
     * @return
     */
    @Override
    public List<InwardRemark> getByInward(Inward inward) {
        return inwardRemarkRepository.findByInwardOrderByIdDescRemarkDateDesc(inward);
    }
}
