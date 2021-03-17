package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;

import java.util.List;
import java.util.Optional;

/**
 * Region interface
 */
public interface RegionService {

    /**
     * Get region
     *
     * @param id
     * @return
     */
    Optional<Region> get(Integer id);

    /**
     * Edit region
     *
     * @param region
     * @return
     */
    Region edit(Region region);

    /**
     * Delete region
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all region basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Region> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all region
     *
     * @return
     */
    List<Region> getAll();

    /**
     * Get region count
     *
     * @return
     */
    Long count();
}
