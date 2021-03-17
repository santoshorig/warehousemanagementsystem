package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Weighbridge;

import java.util.List;
import java.util.Optional;

/**
 * Weighbridge interface
 */
public interface WeighbridgeService {

    /**
     * Get weighbridge
     *
     * @param id
     * @return
     */
    Optional<Weighbridge> get(Integer id);

    /**
     * Edit weighbridge
     *
     * @param weighbridge
     * @return
     */
    Weighbridge edit(Weighbridge weighbridge);

    /**
     * Delete weighbridge
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all weighbridge basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Weighbridge> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all weighbridge
     *
     * @return
     */
    List<Weighbridge> getAll();

    /**
     * Get weighbridge count
     *
     * @return
     */
    Long count();

    /**
     * Get weighbridge by location
     *
     * @param location
     * @return
     */
    List<Weighbridge> getByLocation(Location location);
}
