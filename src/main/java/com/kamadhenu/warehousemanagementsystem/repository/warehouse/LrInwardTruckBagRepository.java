package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrInwardTruckBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Lr Inward Truck Bag repository class
 */
@Repository
public interface LrInwardTruckBagRepository extends JpaRepository<LrInwardTruckBag, Integer> {

    /**
     * Get lr inward truck bag by lr
     *
     * @param lr
     * @return
     */
    List<LrInwardTruckBag> findByLr(Lr lr);

}
