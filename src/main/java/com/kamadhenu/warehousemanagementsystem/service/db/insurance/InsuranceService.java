package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Insurance interface
 */
public interface InsuranceService {

    /**
     * Get insurance
     *
     * @param id
     * @return
     */
    Optional<Insurance> get(Integer id);

    /**
     * Edit insurance
     *
     * @param insurance
     * @return
     */
    Insurance edit(Insurance insurance);

    /**
     * Delete insurance
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Insurance> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all insurance
     *
     * @return
     */
    List<Insurance> getAll();

    /**
     * Get insurance count
     *
     * @return
     */
    Long count();


    /**
     * Get insurance with effective to before certain date
     *
     * @param effectiveTo
     * @return
     */
    List<Insurance> getAllByEffectiveToBefore(Date effectiveTo);
}
