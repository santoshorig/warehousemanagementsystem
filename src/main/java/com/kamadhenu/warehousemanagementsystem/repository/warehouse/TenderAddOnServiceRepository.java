package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderAddOnService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tender Add On Service repository class
 */
@Repository
public interface TenderAddOnServiceRepository extends JpaRepository<TenderAddOnService, Integer> {

    /**
     * Get tender add on service by tender
     *
     * @param tender
     * @return
     */
    List<TenderAddOnService> findByTender(Tender tender);
}
