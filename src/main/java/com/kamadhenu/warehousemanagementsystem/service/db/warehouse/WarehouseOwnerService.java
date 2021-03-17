package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseOwner;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Owner interface
 */
public interface WarehouseOwnerService {

    /**
     * Get warehouse owner
     *
     * @param id
     * @return
     */
    Optional<WarehouseOwner> get(Integer id);

    /**
     * Edit warehouse owner
     *
     * @param warehouseOwner
     * @return
     */
    WarehouseOwner edit(WarehouseOwner warehouseOwner);

    /**
     * Delete warehouse owner
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseOwner> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse owner
     *
     * @return
     */
    List<WarehouseOwner> getAll();

    /**
     * Get warehouse owner count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse owner by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseOwner> getByWarehouse(Warehouse warehouse);
}
