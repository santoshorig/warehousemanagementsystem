package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardRemark;

import java.util.List;
import java.util.Optional;

/**
 * Inward Remark interface
 */
public interface InwardRemarkService {
    /**
     * Get inwardRemark
     *
     * @param id
     * @return
     */
    Optional<InwardRemark> get(Integer id);

    /**
     * Edit inward remark
     *
     * @param inwardRemark
     * @return
     */
    InwardRemark edit(InwardRemark inwardRemark);

    /**
     * Delete inward remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward remark
     *
     * @return
     */
    List<InwardRemark> getAll();

    /**
     * Get inward remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all inward remark basis inward
     *
     * @param inward
     * @return
     */
    List<InwardRemark> getByInward(Inward inward);
}
