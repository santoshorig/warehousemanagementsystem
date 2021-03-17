package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardMadeUpBag;

import java.util.List;
import java.util.Optional;

/**
 * Outward Made Up Bag interface
 */
public interface OutwardMadeUpBagService {

    /**
     * Get outward made up bag
     *
     * @param id
     * @return
     */
    Optional<OutwardMadeUpBag> get(Integer id);

    /**
     * Edit outward made up bag
     *
     * @param outwardMadeUpBag
     * @return
     */
    OutwardMadeUpBag edit(OutwardMadeUpBag outwardMadeUpBag);

    /**
     * Delete outward made up bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward made up bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OutwardMadeUpBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward made up bag
     *
     * @return
     */
    List<OutwardMadeUpBag> getAll();

    /**
     * Get outward made up bag count
     *
     * @return
     */
    Long count();

    /**
     * Get outward made up bag by outward
     *
     * @param outward
     * @return
     */
    List<OutwardMadeUpBag> getByOutward(Outward outward);
}
