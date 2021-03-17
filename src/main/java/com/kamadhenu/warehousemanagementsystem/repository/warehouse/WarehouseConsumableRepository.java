package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseConsumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Consumable repository class
 */
@Repository
public interface WarehouseConsumableRepository extends JpaRepository<WarehouseConsumable, Integer> {

    /**
     * Find warehouse consumable by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseConsumable> findByWarehouse(Warehouse warehouse);
}
