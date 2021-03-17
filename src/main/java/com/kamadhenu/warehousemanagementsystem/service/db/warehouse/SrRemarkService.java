package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;

import java.util.List;
import java.util.Optional;

/**
 * Sr remark interface
 */
public interface SrRemarkService {

    /**
     * Get sr remark
     *
     * @param id
     * @return
     */
    Optional<SrRemark> get(Integer id);

    /**
     * Edit sr remark
     *
     * @param srRemark
     * @return
     */
    SrRemark edit(SrRemark srRemark);

    /**
     * Delete sr remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all sr remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<SrRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all sr remark
     *
     * @return
     */
    List<SrRemark> getAll();

    /**
     * Get sr remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all sr remarks basis sr
     *
     * @param sr
     * @return
     */
    List<SrRemark> getBySr(Sr sr);
}
