package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityAcceptanceLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tender Commodity Acceptance Limit repository class
 */
@Repository
public interface TenderCommodityAcceptanceLimitRepository extends
        JpaRepository<TenderCommodityAcceptanceLimit, Integer> {

    /**
     * Find by tender
     *
     * @param tender
     * @return
     */
    @Query("SELECT tcal from TenderCommodityAcceptanceLimit tcal left join TenderCommodity tc on tcal.tenderCommodity = tc.id WHERE tc.tender = ?1")
    List<TenderCommodityAcceptanceLimit> findByTender(Tender tender);
}
