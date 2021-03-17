package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Stack interface
 */
public interface WarehouseStackService {

    /**
     * Get warehouse stack
     *
     * @param id
     * @return
     */
    Optional<WarehouseStack> get(Integer id);

    /**
     * Edit warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    WarehouseStack edit(WarehouseStack warehouseStack);

    /**
     * Delete warehouse stack
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse stack basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseStack> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse stack
     *
     * @return
     */
    List<WarehouseStack> getAll();

    /**
     * Get warehouse stack count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse stack by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseStack> getByWarehouse(Warehouse warehouse);

    /**
     * Get non full warehouse stack by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseStack> getNonFullByWarehouse(Warehouse warehouse);
}
