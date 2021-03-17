package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Cad repository class
 */
@Repository
public interface WarehouseCadRepository extends JpaRepository<WarehouseCad, Integer> {

    /**
     * Find warehouse cad by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseCad> findByWarehouse(Warehouse warehouse);

    /**
     * Find warehouse cad by warehouse and used
     *
     * @param warehouse
     * @param used
     * @return
     */
    List<WarehouseCad> findByWarehouseAndUsed(Warehouse warehouse, Boolean used);
}
