package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseManpower;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Manpower interface
 */
public interface WarehouseManpowerService {

    /**
     * Get warehouse manpower
     *
     * @param id
     * @return
     */
    Optional<WarehouseManpower> get(Integer id);

    /**
     * Edit warehouse manpower
     *
     * @param warehouseManpower
     * @return
     */
    WarehouseManpower edit(WarehouseManpower warehouseManpower);

    /**
     * Delete warehouse manpower
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse manpower basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseManpower> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse manpower
     *
     * @return
     */
    List<WarehouseManpower> getAll();

    /**
     * Get warehouse manpower count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse manpower by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseManpower> getByWarehouse(Warehouse warehouse);
}
