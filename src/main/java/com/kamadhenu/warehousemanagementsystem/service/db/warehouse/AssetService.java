package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Asset;

import java.util.List;
import java.util.Optional;

/**
 * Asset interface
 */
public interface AssetService {

    /**
     * Get asset
     *
     * @param id
     * @return
     */
    Optional<Asset> get(Integer id);

    /**
     * Edit asset
     *
     * @param asset
     * @return
     */
    Asset edit(Asset asset);

    /**
     * Delete asset
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all asset basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Asset> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all asset
     *
     * @return
     */
    List<Asset> getAll();

    /**
     * Get asset count
     *
     * @return
     */
    Long count();
}
