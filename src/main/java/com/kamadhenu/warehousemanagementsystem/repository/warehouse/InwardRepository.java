package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.InwardStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward repository class
 */
@Repository
public interface InwardRepository extends JpaRepository<Inward, Integer> {

    /**
     * Get inward by status
     *
     * @param statusList
     * @return
     */
    List<Inward> findByStatusIn(List<String> statusList);

    /**
     * Get inward by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Inward> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Get inward by contract
     *
     * @param contract
     * @param statusList
     * @return
     */
    List<Inward> findByContractAndStatusIn(Contract contract, List<String> statusList);

    /**
     * Get inward count by status
     *
     * @return
     */
    @Query("SELECT i.status AS status, COUNT(i.id) AS count FROM Inward i GROUP BY i.status")
    List<InwardStatusCount> countInwardByStatus();

    /**
     * Get inward by cdd number
     *
     * @param cddNumber
     * @return
     */
    List<Inward> findByCddNumber(String cddNumber);
}
