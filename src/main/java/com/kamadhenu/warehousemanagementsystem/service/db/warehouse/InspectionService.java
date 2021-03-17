package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inspection;

import java.util.List;
import java.util.Optional;

/**
 * Inspection interface
 */
public interface InspectionService {

    /**
     * Get inspection
     *
     * @param id
     * @return
     */
    Optional<Inspection> get(Integer id);

    /**
     * Edit inspection
     *
     * @param inspection
     * @return
     */
    Inspection edit(Inspection inspection);

    /**
     * Delete inspection
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inspection basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Inspection> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inspection
     *
     * @return
     */
    List<Inspection> getAll();

    /**
     * Get inspection count
     *
     * @return
     */
    Long count();
}
