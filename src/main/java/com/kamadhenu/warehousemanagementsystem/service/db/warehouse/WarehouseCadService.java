package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCad;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Cad interface
 */
public interface WarehouseCadService {

    /**
     * Get warehouse cad
     *
     * @param id
     * @return
     */
    Optional<WarehouseCad> get(Integer id);

    /**
     * Edit warehouse cad
     *
     * @param warehouseCad
     * @return
     */
    WarehouseCad edit(WarehouseCad warehouseCad);

    /**
     * Delete warehouse cad
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse cad basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseCad> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse cad
     *
     * @return
     */
    List<WarehouseCad> getAll();

    /**
     * Get warehouse cad count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse cad by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseCad> getByWarehouse(Warehouse warehouse);

    /**
     * Get warehouse cad by warehouse and used
     *
     * @param warehouse
     * @param used
     * @return
     */
    List<WarehouseCad> getByWarehouseAndUsed(Warehouse warehouse, Boolean used);
}
