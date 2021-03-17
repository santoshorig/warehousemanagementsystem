package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;

import java.util.List;
import java.util.Optional;

/**
 * UtilizationFactor interface
 */
public interface UtilizationFactorService {

    /**
     * Get utilization factor
     *
     * @param id
     * @return
     */
    Optional<UtilizationFactor> get(Integer id);

    /**
     * Edit utilization factor
     *
     * @param utilizationFactor
     * @return
     */
    UtilizationFactor edit(UtilizationFactor utilizationFactor);

    /**
     * Delete utilization factor
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all utilization factor basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<UtilizationFactor> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all utilization factor
     *
     * @return
     */
    List<UtilizationFactor> getAll();

    /**
     * Get utilization factor count
     *
     * @return
     */
    Long count();

    /**
     * Get by commodity and business type
     *
     * @param commodity
     * @param businessType
     * @return
     */
    List<UtilizationFactor> getByCommodityAndBusinessType(Commodity commodity, BusinessType businessType);
}
