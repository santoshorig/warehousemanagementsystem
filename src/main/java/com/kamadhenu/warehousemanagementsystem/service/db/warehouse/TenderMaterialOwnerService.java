package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderMaterialOwner;

import java.util.List;
import java.util.Optional;

/**
 * Tender Material Owner interface
 */
public interface TenderMaterialOwnerService {

    /**
     * Get tender material owner
     *
     * @param id
     * @return
     */
    Optional<TenderMaterialOwner> get(Integer id);

    /**
     * Edit tender material owner
     *
     * @param tenderMaterialOwner
     * @return
     */
    TenderMaterialOwner edit(TenderMaterialOwner tenderMaterialOwner);

    /**
     * Delete tender material owner
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender material owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderMaterialOwner> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender material owner
     *
     * @return
     */
    List<TenderMaterialOwner> getAll();

    /**
     * Get tender material owner count
     *
     * @return
     */
    Long count();
}
