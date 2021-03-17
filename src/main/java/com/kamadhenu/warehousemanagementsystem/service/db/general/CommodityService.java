package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;

import java.util.List;
import java.util.Optional;

/**
 * Commodity interface
 */
public interface CommodityService {

    /**
     * Get commodity
     *
     * @param id
     * @return
     */
    Optional<Commodity> get(Integer id);

    /**
     * Edit commodity
     *
     * @param commodity
     * @return
     */
    Commodity edit(Commodity commodity);

    /**
     * Delete commodity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Commodity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all commodity
     *
     * @return
     */
    List<Commodity> getAll();

    /**
     * Get commodity count
     *
     * @return
     */
    Long count();
}
