package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Consumable;

import java.util.List;
import java.util.Optional;

/**
 * Consumable interface
 */
public interface ConsumableService {

    /**
     * Get consumable
     *
     * @param id
     * @return
     */
    Optional<Consumable> get(Integer id);

    /**
     * Edit consumable
     *
     * @param consumable
     * @return
     */
    Consumable edit(Consumable consumable);

    /**
     * Delete consumable
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all consumable basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Consumable> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all consumable
     *
     * @return
     */
    List<Consumable> getAll();

    /**
     * Get consumable count
     *
     * @return
     */
    Long count();
}
