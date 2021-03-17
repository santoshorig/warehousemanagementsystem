package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.Margin;

import java.util.List;
import java.util.Optional;

/**
 * Margin interface
 */
public interface MarginService {

    /**
     * Get margin
     *
     * @param id
     * @return
     */
    Optional<Margin> get(Integer id);

    /**
     * Edit margin
     *
     * @param margin
     * @return
     */
    Margin edit(Margin margin);

    /**
     * Delete margin
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all margin basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Margin> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all margin
     *
     * @return
     */
    List<Margin> getAll();

    /**
     * Get margin count
     *
     * @return
     */
    Long count();
}
