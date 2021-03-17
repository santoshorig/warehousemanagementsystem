package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Manpower;

import java.util.List;
import java.util.Optional;

/**
 * Manpower interface
 */
public interface ManpowerService {

    /**
     * Get manpower
     *
     * @param id
     * @return
     */
    Optional<Manpower> get(Integer id);

    /**
     * Edit manpower
     *
     * @param manpower
     * @return
     */
    Manpower edit(Manpower manpower);

    /**
     * Delete manpower
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all manpower basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Manpower> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all manpower
     *
     * @return
     */
    List<Manpower> getAll();

    /**
     * Get manpower count
     *
     * @return
     */
    Long count();
}
