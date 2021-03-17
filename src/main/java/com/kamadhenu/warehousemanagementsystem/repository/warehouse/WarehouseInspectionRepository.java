package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Inspection repository class
 */
@Repository
public interface WarehouseInspectionRepository extends JpaRepository<WarehouseInspection, Integer> {

    /**
     * Find warehouse inspection by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseInspection> findByWarehouse(Warehouse warehouse);

}
