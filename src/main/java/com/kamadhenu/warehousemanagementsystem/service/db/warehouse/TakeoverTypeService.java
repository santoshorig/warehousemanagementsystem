package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TakeoverType;

import java.util.List;
import java.util.Optional;

/**
 * Takeover Type interface
 */
public interface TakeoverTypeService {

    /**
     * Get takeover type
     *
     * @param id
     * @return
     */
    Optional<TakeoverType> get(Integer id);

    /**
     * Edit takeover type
     *
     * @param takeoverType
     * @return
     */
    TakeoverType edit(TakeoverType takeoverType);

    /**
     * Delete takeover type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all takeover type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TakeoverType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all takeover type
     *
     * @return
     */
    List<TakeoverType> getAll();

    /**
     * Get takeover type count
     *
     * @return
     */
    Long count();

    /**
     * Get takeover type by business type
     *
     * @return
     */
    List<TakeoverType> getByBusinessType();
}
