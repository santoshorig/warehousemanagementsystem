package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseManpower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Manpower repository class
 */
@Repository
public interface WarehouseManpowerRepository extends JpaRepository<WarehouseManpower, Integer> {

    /**
     * Find warehouse manpower by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseManpower> findByWarehouse(Warehouse warehouse);
}
