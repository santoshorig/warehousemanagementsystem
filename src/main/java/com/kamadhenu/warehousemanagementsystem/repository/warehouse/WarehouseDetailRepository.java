package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Detail repository class
 */
@Repository
public interface WarehouseDetailRepository extends JpaRepository<WarehouseDetail, Integer> {

    /**
     * Find warehouse detail by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseDetail> findByWarehouse(Warehouse warehouse);
}
