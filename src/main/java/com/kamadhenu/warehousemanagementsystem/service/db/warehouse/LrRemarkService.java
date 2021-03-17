package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;

import java.util.List;
import java.util.Optional;

/**
 * Lr Remark interface
 */
public interface LrRemarkService {

    /**
     * Get lr remark
     *
     * @param id
     * @return
     */
    Optional<LrRemark> get(Integer id);

    /**
     * Edit lr remark
     *
     * @param lrRemark
     * @return
     */
    LrRemark edit(LrRemark lrRemark);

    /**
     * Delete lr remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all lr remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<LrRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all lr remark
     *
     * @return
     */
    List<LrRemark> getAll();

    /**
     * Get lr remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all lr remarks basis lr
     *
     * @param lr
     * @return
     */
    List<LrRemark> getByLr(Lr lr);
}
