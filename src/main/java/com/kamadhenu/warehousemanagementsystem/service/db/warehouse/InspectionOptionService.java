package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionOption;

import java.util.List;
import java.util.Optional;

/**
 * Inspection Option interface
 */
public interface InspectionOptionService {

    /**
     * Get inspection option
     *
     * @param id
     * @return
     */
    Optional<InspectionOption> get(Integer id);

    /**
     * Edit inspection option
     *
     * @param inspectionOption
     * @return
     */
    InspectionOption edit(InspectionOption inspectionOption);

    /**
     * Delete inspection option
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inspection option basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InspectionOption> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inspection option
     *
     * @return
     */
    List<InspectionOption> getAll();

    /**
     * Get inspection option count
     *
     * @return
     */
    Long count();
}
