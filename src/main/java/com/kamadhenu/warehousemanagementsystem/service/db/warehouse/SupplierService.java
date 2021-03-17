package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * Supplier interface
 */
public interface SupplierService {

    /**
     * Get supplier
     *
     * @param id
     * @return
     */
    Optional<Supplier> get(Integer id);

    /**
     * Edit supplier
     *
     * @param supplier
     * @return
     */
    Supplier edit(Supplier supplier);

    /**
     * Delete supplier
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all supplier basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Supplier> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all supplier
     *
     * @return
     */
    List<Supplier> getAll();

    /**
     * Get supplier count
     *
     * @return
     */
    Long count();
}
