package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Inspection Option repository class
 */
@Repository
public interface InspectionOptionRepository extends JpaRepository<InspectionOption, Integer> {

}
