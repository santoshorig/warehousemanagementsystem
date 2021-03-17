package com.kamadhenu.warehousemanagementsystem.service.db.risk;

import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;

import java.util.List;
import java.util.Optional;

/**
 * HighmarkRiskCategory interface
 */
public interface HighmarkRiskCategoryService {

    /**
     * Get highmark risk category
     *
     * @param id
     * @return
     */
    Optional<HighmarkRiskCategory> get(Integer id);

    /**
     * Edit highmark risk category
     *
     * @param highmarkRiskCategory
     * @return
     */
    HighmarkRiskCategory edit(HighmarkRiskCategory highmarkRiskCategory);

    /**
     * Delete highmark risk category
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all highmark risk category basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<HighmarkRiskCategory> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all highmark risk category
     *
     * @return
     */
    List<HighmarkRiskCategory> getAll();

    /**
     * Get highmark risk category count
     *
     * @return
     */
    Long count();
}
