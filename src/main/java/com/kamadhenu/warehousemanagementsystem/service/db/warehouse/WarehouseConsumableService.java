package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseConsumable;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Consumable interface
 */
public interface WarehouseConsumableService {

    /**
     * Get warehouse consumable
     *
     * @param id
     * @return
     */
    Optional<WarehouseConsumable> get(Integer id);

    /**
     * Edit warehouse consumable
     *
     * @param warehouseConsumable
     * @return
     */
    WarehouseConsumable edit(WarehouseConsumable warehouseConsumable);

    /**
     * Delete warehouse consumable
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse consumable basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseConsumable> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse consumable
     *
     * @return
     */
    List<WarehouseConsumable> getAll();

    /**
     * Get warehouse consumable count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse consumable by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseConsumable> getByWarehouse(Warehouse warehouse);
}
