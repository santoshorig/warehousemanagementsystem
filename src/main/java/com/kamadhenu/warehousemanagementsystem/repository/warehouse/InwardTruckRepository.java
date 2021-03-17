package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.ReportingDateWeightCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Inward Truck repository class
 */
@Repository
public interface InwardTruckRepository extends JpaRepository<InwardTruck, Integer> {

    /**
     * Find inward truck by inward
     *
     * @param inward
     * @return
     */
    List<InwardTruck> findByInward(Inward inward);

    /**
     * Get inward truck weight by reporting date
     *
     * @param statusList
     * @param fromDate
     * @param toDate
     * @return
     */
    @Query("SELECT it.dumpingDate as reportingDate, SUM(it.grossWeight) AS weight FROM InwardTruck it" +
            " LEFT JOIN Inward i ON it.inward = i WHERE i.status IN (?1) AND it.dumpingDate >= ?2 AND it.dumpingDate <= ?3 GROUP BY it.dumpingDate")
    List<ReportingDateWeightCount> sumWeightByDateAndStatus(List<String> statusList, Date fromDate, Date toDate);
}
