package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Asset repository class
 */
@Repository
public interface WarehouseAssetRepository extends JpaRepository<WarehouseAsset, Integer> {

    /**
     * Find warehouse asset by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseAsset> findByWarehouse(Warehouse warehouse);
}
