package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;

import java.util.List;
import java.util.Optional;

/**
 * Inward Truck Quality Check interface
 */
public interface InwardTruckQualityCheckService {

    /**
     * Get inward truck quality check
     *
     * @param id
     * @return
     */
    Optional<InwardTruckQualityCheck> get(Integer id);

    /**
     * Edit inward truck quality check
     *
     * @param inwardTruckQualityCheck
     * @return
     */
    InwardTruckQualityCheck edit(InwardTruckQualityCheck inwardTruckQualityCheck);

    /**
     * Delete inward truck quality check
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward truck quality check
     *
     * @return
     */
    List<InwardTruckQualityCheck> getAll();

    /**
     * Get inward truck quality check count
     *
     * @return
     */
    Long count();

    /**
     * Get inward truck quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckQualityCheck> getByInwardTruck(InwardTruck inwardTruck);
}
