package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosureRemark;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Closure Remark interface
 */
public interface WarehouseClosureRemarkService {

    /**
     * Get warehouse closure remark
     *
     * @param id
     * @return
     */
    Optional<WarehouseClosureRemark> get(Integer id);

    /**
     * Edit warehouse closure remark
     *
     * @param warehouseClosure
     * @return
     */
    WarehouseClosureRemark edit(WarehouseClosureRemark warehouseClosure);

    /**
     * Delete warehouse closure remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse closure remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseClosureRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse closure remark
     *
     * @return
     */
    List<WarehouseClosureRemark> getAll();

    /**
     * Get warehouse closure remark count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse closure remark by warehouse closure
     *
     * @param warehouseClosure
     * @return
     */
    List<WarehouseClosureRemark> getByWarehouseClosure(WarehouseClosure warehouseClosure);
}
