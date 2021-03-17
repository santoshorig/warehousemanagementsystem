package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodity;

import java.util.List;
import java.util.Optional;

/**
 * Tender Commodity interface
 */
public interface TenderCommodityService {

    /**
     * Get tender commodity
     *
     * @param id
     * @return
     */
    Optional<TenderCommodity> get(Integer id);

    /**
     * Edit tender commodity
     *
     * @param tenderCommodity
     * @return
     */
    TenderCommodity edit(TenderCommodity tenderCommodity);

    /**
     * Delete tender commodity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderCommodity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender commodity
     *
     * @return
     */
    List<TenderCommodity> getAll();

    /**
     * Get tender commodity count
     *
     * @return
     */
    Long count();

    /**
     * Get tender commodity
     *
     * @param tender
     * @return
     */
    List<TenderCommodity> getByTender(Tender tender);
}
