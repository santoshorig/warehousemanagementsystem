package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Commodity interface
 */
public interface WarehouseCommodityService {

    /**
     * Get warehouse commodity
     *
     * @param id
     * @return
     */
    Optional<WarehouseCommodity> get(Integer id);

    /**
     * Edit warehouse commodity
     *
     * @param warehouseCommodity
     * @return
     */
    WarehouseCommodity edit(WarehouseCommodity warehouseCommodity);

    /**
     * Delete warehouse commodity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseCommodity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse commodity
     *
     * @return
     */
    List<WarehouseCommodity> getAll();

    /**
     * Get warehouse commodity count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse commodity by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseCommodity> getByWarehouse(Warehouse warehouse);
}
