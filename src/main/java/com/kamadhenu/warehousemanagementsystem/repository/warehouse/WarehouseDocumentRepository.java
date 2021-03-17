package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Document repository class
 */
@Repository
public interface WarehouseDocumentRepository extends JpaRepository<WarehouseDocument, Integer> {

    /**
     * Find warehouse document by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseDocument> findByWarehouse(Warehouse warehouse);

}
