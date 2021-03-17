package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Closure repository class
 */
@Repository
public interface WarehouseClosureRepository extends JpaRepository<WarehouseClosure, Integer> {

    /**
     * Find warehouse closure by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseClosure> findByWarehouse(Warehouse warehouse);

    /**
     * Get warehouse closure by status
     *
     * @param statusList
     * @return
     */
    List<WarehouseClosure> findByStatusIn(List<String> statusList);
}
