package com.kamadhenu.warehousemanagementsystem.repository.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.SpotPrice;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Spot Price repository class
 */
@Repository
public interface SpotPriceRepository extends JpaRepository<SpotPrice, Integer> {

    /**
     * Find spot price by commodity and from date and to date and location
     *
     * @param commodity
     * @param fromDate
     * @param toDate
     * @param location
     * @return
     */
    List<SpotPrice> findByCommodityAndFromDateLessThanEqualAndToDateGreaterThanEqualAndLocation(
            Commodity commodity,
            Date fromDate,
            Date toDate,
            Location location
    );
}
