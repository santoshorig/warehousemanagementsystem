package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;

import java.util.List;
import java.util.Optional;

/**
 * BusinessType interface
 */
public interface BusinessTypeService {

    /**
     * Get business type
     *
     * @param id
     * @return
     */
    Optional<BusinessType> get(Integer id);

    /**
     * Edit business type
     *
     * @param businessType
     * @return
     */
    BusinessType edit(BusinessType businessType);

    /**
     * Delete business type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all business type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<BusinessType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all business type
     *
     * @return
     */
    List<BusinessType> getAll();

    /**
     * Get business type count
     *
     * @return
     */
    Long count();
}
