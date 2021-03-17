package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.SpotPrice;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * SpotPrice interface
 */
public interface SpotPriceService {

    /**
     * Get spot price
     *
     * @param id
     * @return
     */
    Optional<SpotPrice> get(Integer id);

    /**
     * Edit spot price
     *
     * @param spotPrice
     * @return
     */
    SpotPrice edit(SpotPrice spotPrice);

    /**
     * Delete spot price
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all spot price basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<SpotPrice> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all spot price
     *
     * @return
     */
    List<SpotPrice> getAll();

    /**
     * Get spot price count
     *
     * @return
     */
    Long count();

    /**
     * Get spot price by commodity and from date and to date and location
     *
     * @param commodity
     * @param fromDate
     * @param toDate
     * @param location
     * @return
     */
    List<SpotPrice> getByCommodityAndFromDateAndToDateAndLocation(
            Commodity commodity,
            Date fromDate,
            Date toDate,
            Location location
    );
}
