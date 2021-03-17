package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Owner repository class
 */
@Repository
public interface WarehouseOwnerRepository extends JpaRepository<WarehouseOwner, Integer> {

    /**
     * Get warehouse owner by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseOwner> findByWarehouse(Warehouse warehouse);
}
