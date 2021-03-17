package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckBag;

import java.util.List;
import java.util.Optional;

/**
 * Outward Truck Bag interface
 */
public interface OutwardTruckBagService {

    /**
     * Get outward truck bag
     *
     * @param id
     * @return
     */
    Optional<OutwardTruckBag> get(Integer id);

    /**
     * Edit outward truck bag
     *
     * @param outwardTruckBag
     * @return
     */
    OutwardTruckBag edit(OutwardTruckBag outwardTruckBag);

    /**
     * Delete outward truck bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OutwardTruckBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward truck bag
     *
     * @return
     */
    List<OutwardTruckBag> getAll();

    /**
     * Get outward truck bag count
     *
     * @return
     */
    Long count();

    /**
     * Get outward truck bag by outward truck
     *
     * @param outwardTruck
     * @return
     */
    List<OutwardTruckBag> getByOutwardTruck(OutwardTruck outwardTruck);
}
