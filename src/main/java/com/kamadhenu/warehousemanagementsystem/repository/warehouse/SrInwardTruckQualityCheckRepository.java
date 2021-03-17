package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sr Inward Truck Quality Check repository class
 */
@Repository
public interface SrInwardTruckQualityCheckRepository extends JpaRepository<SrInwardTruckQualityCheck, Integer> {

    /**
     * Find inward truck quality check by sr
     *
     * @param sr
     * @return
     */
    List<SrInwardTruckQualityCheck> findBySr(Sr sr);

    /**
     * Find inward try quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<SrInwardTruckQualityCheck> findByInwardTruck(InwardTruck inwardTruck);
}
