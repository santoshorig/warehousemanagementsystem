package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityInsurance;

import java.util.List;
import java.util.Optional;

/**
 * Tender Commodity Insurance interface
 */
public interface TenderCommodityInsuranceService {

    /**
     * Get tender commodity insurance
     *
     * @param id
     * @return
     */
    Optional<TenderCommodityInsurance> get(Integer id);

    /**
     * Edit tender commodity insurance
     *
     * @param tenderCommodityInsurance
     * @return
     */
    TenderCommodityInsurance edit(TenderCommodityInsurance tenderCommodityInsurance);

    /**
     * Delete tender commodity insurance
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender commodity insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderCommodityInsurance> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender commodity insurance
     *
     * @return
     */
    List<TenderCommodityInsurance> getAll();

    /**
     * Get tender commodity insurance count
     *
     * @return
     */
    Long count();

    /**
     * Get tender commodity insurance
     *
     * @param tender
     * @return
     */
    List<TenderCommodityInsurance> getByTender(Tender tender);
}
