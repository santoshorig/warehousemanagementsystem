package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardRemarkServiceImpl implements OutwardRemarkService {

    @Autowired
    private OutwardRemarkRepository outwardRemarkRepository;

    /**
     * Get outward remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<OutwardRemark> get(Integer id) {
        return outwardRemarkRepository.findById(id);
    }

    /**
     * Edit outward remark
     *
     * @param OutwardRemark
     * @return
     */
    @Override
    public OutwardRemark edit(OutwardRemark OutwardRemark) {
        return outwardRemarkRepository.save(OutwardRemark);
    }

    /**
     * Delete outward remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardRemarkRepository.deleteById(id);
    }

    /**
     * Get all outward remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<OutwardRemark> getAll(Integer pageNumber, Integer pageSize) {
        return outwardRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward remark
     *
     * @return
     */
    @Override
    public List<OutwardRemark> getAll() {
        return outwardRemarkRepository.findAll();
    }

    /**
     * Get outward remark count
     *
     * @return
     */
    public Long count() {
        return outwardRemarkRepository.count();
    }

    /**
     * Get outward remark
     *
     * @param outward
     * @return
     */
    @Override
    public List<OutwardRemark> getByOutward(Outward outward) {
        return outwardRemarkRepository.findByOutwardOrderByIdDescRemarkDateDesc(outward);
    }
}
