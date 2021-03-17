package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;

import java.util.List;
import java.util.Optional;

/**
 * Outward Truck Quality Check interface
 */
public interface OutwardTruckQualityCheckService {

    /**
     * Get outward truck quality check
     *
     * @param id
     * @return
     */
    Optional<OutwardTruckQualityCheck> get(Integer id);

    /**
     * Edit outward truck quality check
     *
     * @param outwardTruckQualityCheck
     * @return
     */
    OutwardTruckQualityCheck edit(OutwardTruckQualityCheck outwardTruckQualityCheck);

    /**
     * Delete outward truck quality check
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OutwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward truck quality check
     *
     * @return
     */
    List<OutwardTruckQualityCheck> getAll();

    /**
     * Get outward truck quality check count
     *
     * @return
     */
    Long count();

    /**
     * Get outward truck quality check by outward truck
     *
     * @param outwardTruck
     * @return
     */
    List<OutwardTruckQualityCheck> getByOutwardTruck(OutwardTruck outwardTruck);
}
