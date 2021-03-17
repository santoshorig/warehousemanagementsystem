package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward Truck Bag repository class
 */
@Repository
public interface OutwardTruckBagRepository extends JpaRepository<OutwardTruckBag, Integer> {

    /**
     * Find outward truck bag by outward truck
     *
     * @param outwardTruck
     * @return
     */
    List<OutwardTruckBag> findByOutwardTruck(OutwardTruck outwardTruck);
}
