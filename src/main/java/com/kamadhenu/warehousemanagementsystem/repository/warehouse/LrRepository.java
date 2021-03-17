package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.LrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Lr repository class
 */
@Repository
public interface LrRepository extends JpaRepository<Lr, Integer> {

    /**
     * Get lr by status
     *
     * @param statusList
     * @return
     */
    List<Lr> findByStatusIn(List<String> statusList);

    /**
     * Get lr by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Lr> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Find lr by sr
     *
     * @param sr
     * @return
     */
    List<Lr> findBySr(Sr sr);

    /**
     * Find lr by sr and status
     *
     * @param sr
     * @param statusList
     * @return
     */
    List<Lr> findBySrAndStatusIn(Sr sr, List<String> statusList);

    /**
     * Get lr count by status
     *
     * @return
     */
    @Query("SELECT l.status AS status, COUNT(l.id) AS count FROM Lr l GROUP BY l.status")
    List<LrStatusCount> countLrByStatus();
}
