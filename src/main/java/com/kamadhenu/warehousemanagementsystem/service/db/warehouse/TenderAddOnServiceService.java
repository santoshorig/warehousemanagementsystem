package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderAddOnService;

import java.util.List;
import java.util.Optional;

/**
 * Tender Add On Service interface
 */
public interface TenderAddOnServiceService {

    /**
     * Get tender add on service
     *
     * @param id
     * @return
     */
    Optional<TenderAddOnService> get(Integer id);

    /**
     * Edit tender add on service
     *
     * @param tenderAddOnService
     * @return
     */
    TenderAddOnService edit(TenderAddOnService tenderAddOnService);

    /**
     * Delete tender add on service
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderAddOnService> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender add on service
     *
     * @return
     */
    List<TenderAddOnService> getAll();

    /**
     * Get tender add on service count
     *
     * @return
     */
    Long count();

    /**
     * Get tender add on service
     *
     * @param tender
     * @return
     */
    List<TenderAddOnService> getByTender(Tender tender);
}
