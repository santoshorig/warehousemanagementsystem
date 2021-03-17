package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Inspection type repository class
 */
@Repository
public interface InspectionTypeRepository extends JpaRepository<InspectionType, Integer> {

}
