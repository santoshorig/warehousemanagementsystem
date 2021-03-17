package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;

import java.util.List;
import java.util.Optional;

/**
 * Inward Truck Bag interface
 */
public interface InwardTruckBagService {

    /**
     * Get inward truck bag
     *
     * @param id
     * @return
     */
    Optional<InwardTruckBag> get(Integer id);

    /**
     * Edit inward truck bag
     *
     * @param inwardTruckBag
     * @return
     */
    InwardTruckBag edit(InwardTruckBag inwardTruckBag);
    
    /**
     * Edit bulk inward truck bag
     *
     * @param inwardTruckBag
     * @return
     */
    List<InwardTruckBag> editBulk(List<InwardTruckBag> inwardTruckBag);

    /**
     * Delete inward truck bag
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardTruckBag> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward truck bag
     *
     * @return
     */
    List<InwardTruckBag> getAll();

    /**
     * Get inward truck bag count
     *
     * @return
     */
    Long count();

    /**
     * Get inward truck bag by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckBag> getByInwardTruck(InwardTruck inwardTruck);

    /**
     * Get inward truck bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    List<InwardTruckBag> getByWarehouseStack(WarehouseStack warehouseStack);

    /**
     * Get inward truck bag available for do by inward truck and warehouse stack and bag type
     *
     * @param inwardTruck
     * @param warehouseStack
     * @param bagType
     * @return
     */
    List<InwardTruckBag> getAvailableForDoByInwardTruckAndWarehouseStackAndBagType(
            InwardTruck inwardTruck,
            WarehouseStack warehouseStack,
            BagType bagType
    );
}
