package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck;

import java.util.List;
import java.util.Optional;

/**
 * Sr Inward Truck Quality Check interface
 */
public interface SrInwardTruckQualityCheckService {

    /**
     * Get sr inward truck quality check
     *
     * @param id
     * @return
     */
    Optional<SrInwardTruckQualityCheck> get(Integer id);

    /**
     * Edit sr inward truck quality check
     *
     * @param srInwardTruckQualityCheck
     * @return
     */
    SrInwardTruckQualityCheck edit(SrInwardTruckQualityCheck srInwardTruckQualityCheck);

    /**
     * Delete sr inward truck quality check
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all sr inward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<SrInwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all sr inward truck quality check
     *
     * @return
     */
    List<SrInwardTruckQualityCheck> getAll();

    /**
     * Get sr inward truck quality check count
     *
     * @return
     */
    Long count();

    /**
     * Get sr inward truck quality check by sr
     *
     * @param sr
     * @return
     */
    List<SrInwardTruckQualityCheck> getBySr(Sr sr);

    /**
     * Get sr inward truck quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<SrInwardTruckQualityCheck> getByInwardTruck(InwardTruck inwardTruck);
}
