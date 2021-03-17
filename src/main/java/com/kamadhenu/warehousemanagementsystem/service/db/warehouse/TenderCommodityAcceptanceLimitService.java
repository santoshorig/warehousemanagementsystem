package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityAcceptanceLimit;

import java.util.List;
import java.util.Optional;

/**
 * Tender Commodity Acceptance Limit interface
 */
public interface TenderCommodityAcceptanceLimitService {

    /**
     * Get tender commodity acceptance limit
     *
     * @param id
     * @return
     */
    Optional<TenderCommodityAcceptanceLimit> get(Integer id);

    /**
     * Edit tender commodity acceptance limit
     *
     * @param tenderCommodityAcceptanceLimit
     * @return
     */
    TenderCommodityAcceptanceLimit edit(TenderCommodityAcceptanceLimit tenderCommodityAcceptanceLimit);

    /**
     * Delete tender commodity acceptance limit
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender commodity acceptance limit basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderCommodityAcceptanceLimit> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender commodity acceptance limit
     *
     * @return
     */
    List<TenderCommodityAcceptanceLimit> getAll();

    /**
     * Get tender commodity acceptance limit count
     *
     * @return
     */
    Long count();

    /**
     * Get by tender
     *
     * @param tender
     * @return
     */
    List<TenderCommodityAcceptanceLimit> getByTender(Tender tender);
}
