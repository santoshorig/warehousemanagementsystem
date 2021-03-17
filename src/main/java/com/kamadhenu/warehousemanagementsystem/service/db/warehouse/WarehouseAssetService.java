package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseAsset;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Asset interface
 */
public interface WarehouseAssetService {

    /**
     * Get warehouse asset
     *
     * @param id
     * @return
     */
    Optional<WarehouseAsset> get(Integer id);

    /**
     * Edit warehouse asset
     *
     * @param warehouseAsset
     * @return
     */
    WarehouseAsset edit(WarehouseAsset warehouseAsset);

    /**
     * Delete warehouse asset
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse asset basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseAsset> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse asset
     *
     * @return
     */
    List<WarehouseAsset> getAll();

    /**
     * Get warehouse asset count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse asset by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseAsset> getByWarehouse(Warehouse warehouse);
}
