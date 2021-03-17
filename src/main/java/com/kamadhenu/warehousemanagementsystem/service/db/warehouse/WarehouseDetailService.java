package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Detail interface
 */
public interface WarehouseDetailService {

    /**
     * Get warehouse detail
     *
     * @param id
     * @return
     */
    Optional<WarehouseDetail> get(Integer id);

    /**
     * Edit warehouse detail
     *
     * @param warehouseDetail
     * @return
     */
    WarehouseDetail edit(WarehouseDetail warehouseDetail);

    /**
     * Delete warehouse detail
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse detail basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseDetail> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse detail
     *
     * @return
     */
    List<WarehouseDetail> getAll();

    /**
     * Get warehouse detail count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse detail by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseDetail> getByWarehouse(Warehouse warehouse);
}
