package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward repository class
 */
@Repository
public interface OutwardRepository extends JpaRepository<Outward, Integer> {

    /**
     * Get outward by status
     *
     * @param statusList
     * @return
     */
    List<Outward> findByStatusIn(List<String> statusList);

    /**
     * Get outward by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Outward> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Get outward by Do
     *
     * @param doModel
     * @param statusList
     * @return
     */
    List<Outward> findByDoModelAndStatusIn(Do doModel, List<String> statusList);

    /**
     * Get outward by cdf number
     *
     * @param cdfNumber
     * @return
     */
    List<Outward> findByCdfNumber(String cdfNumber);
}
