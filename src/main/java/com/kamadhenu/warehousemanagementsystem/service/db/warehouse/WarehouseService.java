package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse interface
 */
public interface WarehouseService {

    /**
     * Get warehouse
     *
     * @param id
     * @return
     */
    Optional<Warehouse> get(Integer id);

    /**
     * Edit warehouse
     *
     * @param warehouse
     * @return
     */
    Warehouse edit(Warehouse warehouse);

    /**
     * Delete warehouse
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Warehouse> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse
     *
     * @return
     */
    List<Warehouse> getAll();

    /**
     * Get warehouse count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse by status
     *
     * @return
     */
    List<Warehouse> getByStatus();

    /**
     * Get warehouse by status
     *
     * @param statusList
     * @return
     */
    List<Warehouse> getByStatus(List<String> statusList);

    /**
     * Get warehouse by status and business type
     *
     * @return
     */
    List<Warehouse> getByStatusAndBusinessType();

    /**
     * Get active warehouse by business type
     *
     * @return
     */
    List<Warehouse> getActiveByBusinessType();

    /**
     * Get warehouse count by location
     *
     * @return
     */
    List<WarehouseLocationCount> getCountWarehouseByLocation();

    /**
     * Get warehouse count by status
     *
     * @return
     */
    List<WarehouseStatusCount> getCountWarehouseByStatus();
}
