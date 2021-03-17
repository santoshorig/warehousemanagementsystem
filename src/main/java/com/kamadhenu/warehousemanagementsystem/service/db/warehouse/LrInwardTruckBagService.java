package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrInwardTruckBag;

import java.util.List;
import java.util.Optional;

/**
 * Lr Inward Truck Bag interface
 */
public interface LrInwardTruckBagService {

    /**
     * Get lr inward truck bag
     *
     * @param id
     * @return
     */
    Optional<LrInwardTruckBag> get(Integer id);

    /**
     * Edit lr inward truck bag
     *
     * @param lrRemark
     * @return
     */
    LrInwardTruckBag edit(LrInwardTruckBag lrRemark);

    /**
     * Delete lr inward truck bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all lr inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<LrInwardTruckBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all lr inward truck bag
     *
     * @return
     */
    List<LrInwardTruckBag> getAll();

    /**
     * Get lr inward truck bag count
     *
     * @return
     */
    Long count();

    /**
     * Get all lr inward truck bags basis lr
     *
     * @param lr
     * @return
     */
    List<LrInwardTruckBag> getByLr(Lr lr);
}
