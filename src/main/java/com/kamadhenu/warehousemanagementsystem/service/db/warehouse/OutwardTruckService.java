package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.ReportingDateWeightCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Outward Truck interface
 */
public interface OutwardTruckService {

    /**
     * Get outward truck
     *
     * @param id
     * @return
     */
    Optional<OutwardTruck> get(Integer id);

    /**
     * Edit outward truck
     *
     * @param outwardTruck
     * @return
     */
    OutwardTruck edit(OutwardTruck outwardTruck);

    /**
     * Delete outward truck
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward truck basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OutwardTruck> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward truck
     *
     * @return
     */
    List<OutwardTruck> getAll();

    /**
     * Get outward truck count
     *
     * @return
     */
    Long count();

    /**
     * Get outward truck by outward
     *
     * @param outward
     * @return
     */
    List<OutwardTruck> getByOutward(Outward outward);
}
