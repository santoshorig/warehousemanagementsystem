package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;

import java.util.List;
import java.util.Optional;

/**
 * Outward interface
 */
public interface OutwardService {

    /**
     * Get outward
     *
     * @param id
     * @return
     */
    Optional<Outward> get(Integer id);

    /**
     * Edit outward
     *
     * @param outward
     * @return
     */
    Outward edit(Outward outward);

    /**
     * Delete outward
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all outward basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Outward> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all outward
     *
     * @return
     */
    List<Outward> getAll();

    /**
     * Get outward count
     *
     * @return
     */
    Long count();

    /**
     * Get outwards by status and business type
     *
     * @return
     */
    List<Outward> getByStatusAndBusinessType();

    /**
     * Get outwards by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Outward> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get outward by cdf number
     *
     * @param cdfNumber
     * @return
     */
    List<Outward> getByCdfNumber(String cdfNumber);
}
