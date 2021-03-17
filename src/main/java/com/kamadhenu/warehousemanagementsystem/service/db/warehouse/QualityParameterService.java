package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;

import java.util.List;
import java.util.Optional;

/**
 * Quality Parameter interface
 */
public interface QualityParameterService {

    /**
     * Get quality parameter
     *
     * @param id
     * @return
     */
    Optional<QualityParameter> get(Integer id);

    /**
     * Edit quality parameter
     *
     * @param qualityParameter
     * @return
     */
    QualityParameter edit(QualityParameter qualityParameter);

    /**
     * Delete quality parameter
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all quality parameter basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<QualityParameter> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all quality parameter
     *
     * @return
     */
    List<QualityParameter> getAll();

    /**
     * Get quality parameter count
     *
     * @return
     */
    Long count();
}
