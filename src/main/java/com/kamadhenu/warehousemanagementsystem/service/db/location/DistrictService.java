package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;

import java.util.List;
import java.util.Optional;

/**
 * District interface
 */
public interface DistrictService {

    /**
     * Get district
     *
     * @param id
     * @return
     */
    Optional<District> get(Integer id);

    /**
     * Edit district
     *
     * @param district
     * @return
     */
    District edit(District district);

    /**
     * Delete district
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all district basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<District> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all district
     *
     * @return
     */
    List<District> getAll();

    /**
     * Get district count
     *
     * @return
     */
    Long count();

    /**
     * Get all district by state
     *
     * @param state
     * @return
     */
    List<District> getByState(State state);
}
