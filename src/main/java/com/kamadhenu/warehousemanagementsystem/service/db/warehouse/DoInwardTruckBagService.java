package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;

import java.util.List;
import java.util.Optional;

/**
 * Do Inward Truck Bag interface
 */
public interface DoInwardTruckBagService {

    /**
     * Get do inward truck bag
     *
     * @param id
     * @return
     */
    Optional<DoInwardTruckBag> get(Integer id);

    /**
     * Edit do inward truck bag
     *
     * @param doRemark
     * @return
     */
    DoInwardTruckBag edit(DoInwardTruckBag doRemark);

    /**
     * Delete do inward truck bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all do inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DoInwardTruckBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all do inward truck bag
     *
     * @return
     */
    List<DoInwardTruckBag> getAll();

    /**
     * Get do inward truck bag count
     *
     * @return
     */
    Long count();

    /**
     * Get all do inward truck bags basis do
     *
     * @param doModel
     * @return
     */
    List<DoInwardTruckBag> getByDo(Do doModel);

    /**
     * Get for outward by do inward truck bags basis do
     *
     * @param doModel
     * @return
     */
    List<DoInwardTruckBag> getForOutwardByDo(Do doModel);
}
