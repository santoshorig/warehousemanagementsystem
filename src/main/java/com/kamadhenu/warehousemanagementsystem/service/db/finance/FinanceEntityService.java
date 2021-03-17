package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.FinanceEntity;

import java.util.List;
import java.util.Optional;

/**
 * FinanceEntity interface
 */
public interface FinanceEntityService {

    /**
     * Get finance entity
     *
     * @param id
     * @return
     */
    Optional<FinanceEntity> get(Integer id);

    /**
     * Edit finance entity
     *
     * @param financeEntity
     * @return
     */
    FinanceEntity edit(FinanceEntity financeEntity);

    /**
     * Delete finance entity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all finance entity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<FinanceEntity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all finance entity
     *
     * @return
     */
    List<FinanceEntity> getAll();

    /**
     * Get finance entity count
     *
     * @return
     */
    Long count();
}
