package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Weighbridge repository class
 */
@Repository
public interface WarehouseWeighbridgeRepository extends JpaRepository<WarehouseWeighbridge, Integer> {

    /**
     * Get warehouse weighbridge by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseWeighbridge> findByWarehouse(Warehouse warehouse);
}
