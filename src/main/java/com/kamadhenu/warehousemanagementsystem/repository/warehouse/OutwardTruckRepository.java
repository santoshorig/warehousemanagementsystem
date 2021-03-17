package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward Truck repository class
 */
@Repository
public interface OutwardTruckRepository extends JpaRepository<OutwardTruck, Integer> {

    /**
     * Find outward truck by outward
     *
     * @param outward
     * @return
     */
    List<OutwardTruck> findByOutward(Outward outward);
}
