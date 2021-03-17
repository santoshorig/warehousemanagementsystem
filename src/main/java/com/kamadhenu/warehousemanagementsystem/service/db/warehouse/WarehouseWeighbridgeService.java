package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Weighbridge interface
 */
public interface WarehouseWeighbridgeService {

    /**
     * Get warehouse weighbridge
     *
     * @param id
     * @return
     */
    Optional<WarehouseWeighbridge> get(Integer id);

    /**
     * Edit warehouse weighbridge
     *
     * @param warehouseWeighbridge
     * @return
     */
    WarehouseWeighbridge edit(WarehouseWeighbridge warehouseWeighbridge);

    /**
     * Delete warehouse weighbridge
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse weighbridge basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseWeighbridge> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse weighbridge
     *
     * @return
     */
    List<WarehouseWeighbridge> getAll();

    /**
     * Get warehouse weighbridge count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse weighbridge by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseWeighbridge> getByWarehouse(Warehouse warehouse);
}
