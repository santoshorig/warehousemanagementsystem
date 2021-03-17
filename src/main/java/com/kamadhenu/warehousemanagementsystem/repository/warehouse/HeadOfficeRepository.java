package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.HeadOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Head Office repository class
 */
@Repository
public interface HeadOfficeRepository extends JpaRepository<HeadOffice, Integer> {

}
