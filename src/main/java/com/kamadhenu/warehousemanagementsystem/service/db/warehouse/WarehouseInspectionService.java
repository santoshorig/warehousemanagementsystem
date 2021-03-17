package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Inspection interface
 */
public interface WarehouseInspectionService {

    /**
     * Get warehouse inspection
     *
     * @param id
     * @return
     */
    Optional<WarehouseInspection> get(Integer id);

    /**
     * Edit warehouse inspection
     *
     * @param warehouseInspection
     * @return
     */
    WarehouseInspection edit(WarehouseInspection warehouseInspection);

    /**
     * Delete warehouse inspection
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse inspection basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseInspection> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse inspection
     *
     * @return
     */
    List<WarehouseInspection> getAll();

    /**
     * Get warehouse inspection count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse inspection by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseInspection> getByWarehouse(Warehouse warehouse);
}
