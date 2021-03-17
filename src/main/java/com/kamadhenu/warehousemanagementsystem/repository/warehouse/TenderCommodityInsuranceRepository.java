package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tender Commodity Insurance repository class
 */
@Repository
public interface TenderCommodityInsuranceRepository extends JpaRepository<TenderCommodityInsurance, Integer> {

    /**
     * Find by tender
     *
     * @param tender
     * @return
     */
    @Query("SELECT tci from TenderCommodityInsurance tci left join TenderCommodity tc on tci.tenderCommodity = tc.id WHERE tc.tender = ?1")
    List<TenderCommodityInsurance> findByTender(Tender tender);

}
