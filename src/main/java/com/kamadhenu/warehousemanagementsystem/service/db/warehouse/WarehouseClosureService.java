package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Closure interface
 */
public interface WarehouseClosureService {

    /**
     * Get warehouse closure
     *
     * @param id
     * @return
     */
    Optional<WarehouseClosure> get(Integer id);

    /**
     * Edit warehouse closure
     *
     * @param warehouseClosure
     * @return
     */
    WarehouseClosure edit(WarehouseClosure warehouseClosure);

    /**
     * Delete warehouse closure
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse closure basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseClosure> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse closure
     *
     * @return
     */
    List<WarehouseClosure> getAll();

    /**
     * Get warehouse closure count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse closure by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseClosure> getByWarehouse(Warehouse warehouse);

    /**
     * Get warehouse closure by status
     *
     * @return
     */
    List<WarehouseClosure> getByStatus();
}
