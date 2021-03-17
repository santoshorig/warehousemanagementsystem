package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;

import java.util.List;
import java.util.Optional;

/**
 * Inspection Type interface
 */
public interface InspectionTypeService {

    /**
     * Get inspection type
     *
     * @param id
     * @return
     */
    Optional<InspectionType> get(Integer id);

    /**
     * Edit inspection type
     *
     * @param inspectionOption
     * @return
     */
    InspectionType edit(InspectionType inspectionOption);

    /**
     * Delete inspection type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inspection type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InspectionType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inspection type
     *
     * @return
     */
    List<InspectionType> getAll();

    /**
     * Get inspection type count
     *
     * @return
     */
    Long count();
}
