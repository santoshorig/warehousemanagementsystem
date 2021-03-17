package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tender Commodity repository class
 */
@Repository
public interface TenderCommodityRepository extends JpaRepository<TenderCommodity, Integer> {

    /**
     * Get by tender
     *
     * @param tender
     * @return
     */
    List<TenderCommodity> findByTender(Tender tender);
}
