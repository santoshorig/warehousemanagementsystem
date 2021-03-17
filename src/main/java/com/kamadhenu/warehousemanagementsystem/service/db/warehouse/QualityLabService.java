package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityLab;

import java.util.List;
import java.util.Optional;

/**
 * QualityLab interface
 */
public interface QualityLabService {

    /**
     * Get qualityLab
     *
     * @param id
     * @return
     */
    Optional<QualityLab> get(Integer id);

    /**
     * Edit qualityLab
     *
     * @param qualityLab
     * @return
     */
    QualityLab edit(QualityLab qualityLab);

    /**
     * Delete qualityLab
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all qualityLab basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<QualityLab> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all qualityLab
     *
     * @return
     */
    List<QualityLab> getAll();

    /**
     * Get qualityLab count
     *
     * @return
     */
    Long count();

    /**
     * Get by location
     *
     * @param location
     * @return
     */
    List<QualityLab> getByLocation(Location location);
}
