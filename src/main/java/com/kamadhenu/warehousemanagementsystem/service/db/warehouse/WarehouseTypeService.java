package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseType;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Type interface
 */
public interface WarehouseTypeService {

    /**
     * Get warehouse type
     *
     * @param id
     * @return
     */
    Optional<WarehouseType> get(Integer id);

    /**
     * Edit warehouse type
     *
     * @param warehouseType
     * @return
     */
    WarehouseType edit(WarehouseType warehouseType);

    /**
     * Delete warehouse type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse type
     *
     * @return
     */
    List<WarehouseType> getAll();

    /**
     * Get warehouse type count
     *
     * @return
     */
    Long count();
}
