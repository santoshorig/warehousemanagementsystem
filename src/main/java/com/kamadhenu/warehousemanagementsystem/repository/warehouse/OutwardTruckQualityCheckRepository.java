package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward Truck Quality Check repository class
 */
@Repository
public interface OutwardTruckQualityCheckRepository extends JpaRepository<OutwardTruckQualityCheck, Integer> {

    /**
     * Find outward truck quality check by outward truck
     *
     * @param outwardTruck
     * @return
     */
    List<OutwardTruckQualityCheck> findByOutwardTruck(OutwardTruck outwardTruck);
}
