package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;

import java.util.List;
import java.util.Optional;

/**
 * Do Remark interface
 */
public interface DoRemarkService {

    /**
     * Get do remark
     *
     * @param id
     * @return
     */
    Optional<DoRemark> get(Integer id);

    /**
     * Edit do remark
     *
     * @param doRemark
     * @return
     */
    DoRemark edit(DoRemark doRemark);

    /**
     * Delete do remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all do remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DoRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all do remark
     *
     * @return
     */
    List<DoRemark> getAll();

    /**
     * Get do remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all do remarks basis do
     *
     * @param doModel
     * @return
     */
    List<DoRemark> getByDo(Do doModel);
}
