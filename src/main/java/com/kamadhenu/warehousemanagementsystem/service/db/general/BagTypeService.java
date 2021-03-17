package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;

import java.util.List;
import java.util.Optional;

/**
 * BagType interface
 */
public interface BagTypeService {

    /**
     * Get bag type
     *
     * @param id
     * @return
     */
    Optional<BagType> get(Integer id);

    /**
     * Edit bag type
     *
     * @param bagType
     * @return
     */
    BagType edit(BagType bagType);

    /**
     * Delete bag type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all bag type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<BagType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all bag type
     *
     * @return
     */
    List<BagType> getAll();

    /**
     * Get bag type count
     *
     * @return
     */
    Long count();
}
