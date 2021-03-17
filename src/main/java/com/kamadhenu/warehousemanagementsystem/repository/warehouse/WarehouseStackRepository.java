package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Stack repository class
 */
@Repository
public interface WarehouseStackRepository extends JpaRepository<WarehouseStack, Integer> {

    /**
     * Find warehouse stack by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseStack> findByWarehouse(Warehouse warehouse);

    /**
     * Find warehouse stack by warehouse and full
     *
     * @param warehouse
     * @param full
     * @return
     */
    List<WarehouseStack> findByWarehouseAndFull(Warehouse warehouse, Boolean full);
}
