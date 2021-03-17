package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Do Inward Truck Bag repository class
 */
@Repository
public interface DoInwardTruckBagRepository extends JpaRepository<DoInwardTruckBag, Integer> {

    /**
     * Get do inward truck bag by do
     *
     * @param doModel
     * @return
     */
    List<DoInwardTruckBag> findByDoModel(Do doModel);

    /**
     * Get do inward truck bag by do and outward
     *
     * @param doModel
     * @param outward
     * @return
     */
    List<DoInwardTruckBag> findByDoModelAndOutward(Do doModel, Boolean outward);

}
