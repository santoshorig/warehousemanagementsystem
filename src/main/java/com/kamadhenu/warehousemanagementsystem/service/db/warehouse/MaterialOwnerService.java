package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.MaterialOwner;

import java.util.List;
import java.util.Optional;

/**
 * Material Owner interface
 */
public interface MaterialOwnerService {

    /**
     * Get material owner
     *
     * @param id
     * @return
     */
    Optional<MaterialOwner> get(Integer id);

    /**
     * Edit material owner
     *
     * @param materialOwner
     * @return
     */
    MaterialOwner edit(MaterialOwner materialOwner);

    /**
     * Delete material owner
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all material owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<MaterialOwner> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all material owner
     *
     * @return
     */
    List<MaterialOwner> getAll();

    /**
     * Get material owner count
     *
     * @return
     */
    Long count();
}
