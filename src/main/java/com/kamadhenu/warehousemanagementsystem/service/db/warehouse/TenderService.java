package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;

import java.util.List;
import java.util.Optional;

/**
 * Tender interface
 */
public interface TenderService {

    /**
     * Get tender
     *
     * @param id
     * @return
     */
    Optional<Tender> get(Integer id);

    /**
     * Edit tender
     *
     * @param tender
     * @return
     */
    Tender edit(Tender tender);

    /**
     * Delete tender
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Tender> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender
     *
     * @return
     */
    List<Tender> getAll();

    /**
     * Get tender count
     *
     * @return
     */
    Long count();
}
