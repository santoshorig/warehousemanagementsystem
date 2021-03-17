package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;

import java.util.List;
import java.util.Optional;

/**
 * Region Location interface
 */
public interface RegionLocationService {

    /**
     * Get region location
     *
     * @param id
     * @return
     */
    Optional<RegionLocation> get(Integer id);

    /**
     * Edit region location
     *
     * @param regionLocation
     * @return
     */
    RegionLocation edit(RegionLocation regionLocation);

    /**
     * Delete region location
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all region location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<RegionLocation> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all region location
     *
     * @return
     */
    List<RegionLocation> getAll();

    /**
     * Get region location count
     *
     * @return
     */
    Long count();
}
