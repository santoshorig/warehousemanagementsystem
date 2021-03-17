package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Commodity repository class
 */
@Repository
public interface WarehouseCommodityRepository extends JpaRepository<WarehouseCommodity, Integer> {

    /**
     * Find by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseCommodity> findByWarehouse(Warehouse warehouse);

}
