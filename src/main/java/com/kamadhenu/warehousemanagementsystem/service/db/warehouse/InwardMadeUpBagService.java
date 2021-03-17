package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;

import java.util.List;
import java.util.Optional;

/**
 * Inward Made Up Bag interface
 */
public interface InwardMadeUpBagService {

    /**
     * Get inward made up bag
     *
     * @param id
     * @return
     */
    Optional<InwardMadeUpBag> get(Integer id);

    /**
     * Edit inward made up bag
     *
     * @param inwardMadeUpBag
     * @return
     */
    InwardMadeUpBag edit(InwardMadeUpBag inwardMadeUpBag);

    /**
     * Delete inward made up bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward made up bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardMadeUpBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward made up bag
     *
     * @return
     */
    List<InwardMadeUpBag> getAll();

    /**
     * Get inward made up bag count
     *
     * @return
     */
    Long count();

    /**
     * Get inward made up bag by inward
     *
     * @param inward
     * @return
     */
    List<InwardMadeUpBag> getByInward(Inward inward);

    /**
     * Get inward made up bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    List<InwardMadeUpBag> getByWarehouseStack(WarehouseStack warehouseStack);

    /**
     * Get inward made up bag for outward by inward
     *
     * @param inward
     * @return
     */
    List<InwardMadeUpBag> getForOutwardByInward(Inward inward);
}
