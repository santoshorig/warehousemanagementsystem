package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward Truck Bag repository class
 */
@Repository
public interface InwardTruckBagRepository extends JpaRepository<InwardTruckBag, Integer> {

    /**
     * Find inward truck bag by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckBag> findByInwardTruck(InwardTruck inwardTruck);

    /**
     * Find inward truck bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    List<InwardTruckBag> findByWarehouseStack(WarehouseStack warehouseStack);

    /**
     * Find inward truck bag by inward truck and warehouse stack and bag type
     *
     * @param inwardTruck
     * @param warehouseStack
     * @param bagType
     * @param lien
     * @param doModel
     * @param outward
     * @return
     */
    List<InwardTruckBag> findByInwardTruckAndWarehouseStackAndBagTypeAndLienAndDoModelAndOutward(
            InwardTruck inwardTruck,
            WarehouseStack warehouseStack,
            BagType bagType,
            Boolean lien,
            Boolean doModel,
            Boolean outward
    );
}
