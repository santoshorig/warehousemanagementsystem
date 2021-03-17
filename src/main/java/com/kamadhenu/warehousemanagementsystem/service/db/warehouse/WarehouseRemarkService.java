package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseRemark;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Remark interface
 */
public interface WarehouseRemarkService {
    /**
     * Get warehouse remark
     *
     * @param id
     * @return
     */
    Optional<WarehouseRemark> get(Integer id);

    /**
     * Edit warehouse remark
     *
     * @param warehouseRemark
     * @return
     */
    WarehouseRemark edit(WarehouseRemark warehouseRemark);

    /**
     * Delete warehouse remark
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse remark
     *
     * @return
     */
    List<WarehouseRemark> getAll();

    /**
     * Get warehouse remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all warehouse remark basis warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseRemark> getByWarehouse(Warehouse warehouse);
}
