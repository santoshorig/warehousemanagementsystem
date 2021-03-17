package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tender Client repository class
 */
@Repository
public interface TenderClientRepository extends JpaRepository<TenderClient, Integer> {

}
