package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;

import java.util.List;
import java.util.Optional;

/**
 * Location interface
 */
public interface LocationService {

    /**
     * Get location
     *
     * @param id
     * @return
     */
    Optional<Location> get(Integer id);

    /**
     * Edit location
     *
     * @param location
     * @return
     */
    Location edit(Location location);

    /**
     * Delete location
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Location> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all location
     *
     * @return
     */
    List<Location> getAll();

    /**
     * Get location count
     *
     * @return
     */
    Long count();

    /**
     * Get all locations by districts
     *
     * @param districtList
     * @return
     */
    List<Location> getByDistricts(List<District> districtList);
}
