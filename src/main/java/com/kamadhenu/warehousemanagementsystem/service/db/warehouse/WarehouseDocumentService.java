package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDocument;

import java.util.List;
import java.util.Optional;

/**
 * Warehouse Document interface
 */
public interface WarehouseDocumentService {

    /**
     * Get warehouse document
     *
     * @param id
     * @return
     */
    Optional<WarehouseDocument> get(Integer id);

    /**
     * Edit warehouse document
     *
     * @param warehouseDocument
     * @return
     */
    WarehouseDocument edit(WarehouseDocument warehouseDocument);

    /**
     * Delete warehouse document
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all warehouse document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WarehouseDocument> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all warehouse document
     *
     * @return
     */
    List<WarehouseDocument> getAll();

    /**
     * Get warehouse document count
     *
     * @return
     */
    Long count();

    /**
     * Get warehouse document by warehouse
     *
     * @param warehouse
     * @return
     */
    List<WarehouseDocument> getByWarehouse(Warehouse warehouse);
}
