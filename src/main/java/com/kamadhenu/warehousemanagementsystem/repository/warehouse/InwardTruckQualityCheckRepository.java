package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward Truck Quality Check repository class
 */
@Repository
public interface InwardTruckQualityCheckRepository extends JpaRepository<InwardTruckQualityCheck, Integer> {

    /**
     * Find inward truck quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckQualityCheck> findByInwardTruck(InwardTruck inwardTruck);
}
