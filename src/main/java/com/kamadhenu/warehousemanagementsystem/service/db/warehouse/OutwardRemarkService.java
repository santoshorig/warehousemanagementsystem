package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardRemark;

import java.util.List;
import java.util.Optional;

/**
 * Outward Remark interface
 */
public interface OutwardRemarkService {
    /**
     * Get outwardRemark
     *
     * @param id
     * @return
     */
    Optional<OutwardRemark> get(Integer id);

    /**
     * Edit outward remark
     *
     * @param outwardRemark
     * @return
     */
    OutwardRemark edit(OutwardRemark outwardRemark);

    /**
     * Delete outward remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OutwardRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward remark
     *
     * @return
     */
    List<OutwardRemark> getAll();

    /**
     * Get outward remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all outward remark basis outward
     *
     * @param outward
     * @return
     */
    List<OutwardRemark> getByOutward(Outward outward);
}
