package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;

import java.util.List;
import java.util.Optional;

/**
 * Basis Of Takeover interface
 */
public interface BasisOfTakeoverService {

    /**
     * Get basis of takeover
     *
     * @param id
     * @return
     */
    Optional<BasisOfTakeover> get(Integer id);

    /**
     * Edit basis of takeover
     *
     * @param basisOfTakeover
     * @return
     */
    BasisOfTakeover edit(BasisOfTakeover basisOfTakeover);

    /**
     * Delete basis of takeover
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all basis of takeover basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<BasisOfTakeover> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all basis of takeover
     *
     * @return
     */
    List<BasisOfTakeover> getAll();

    /**
     * Get basis of takeover count
     *
     * @return
     */
    Long count();
}
