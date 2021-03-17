package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WeighbridgeType;

import java.util.List;
import java.util.Optional;

/**
 * Weighbridge Type interface
 */
public interface WeighbridgeTypeService {

    /**
     * Get weighbridge type
     *
     * @param id
     * @return
     */
    Optional<WeighbridgeType> get(Integer id);

    /**
     * Edit weighbridge type
     *
     * @param weighbridgeType
     * @return
     */
    WeighbridgeType edit(WeighbridgeType weighbridgeType);

    /**
     * Delete weighbridge type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all weighbridge type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WeighbridgeType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all weighbridge type
     *
     * @return
     */
    List<WeighbridgeType> getAll();

    /**
     * Get weighbridge type count
     *
     * @return
     */
    Long count();
}
