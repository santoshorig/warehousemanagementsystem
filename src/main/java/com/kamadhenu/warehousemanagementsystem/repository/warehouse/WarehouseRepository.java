package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse repository class
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    /**
     * Get warehouse by status
     *
     * @param statusList
     * @return
     */
    List<Warehouse> findByStatusIn(List<String> statusList);

    /**
     * Get warehouse by status and business type
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Warehouse> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Get warehouse count by location
     *
     * @return
     */
    @Query("SELECT l.id AS location, COUNT(w.id) AS count FROM Warehouse w LEFT JOIN RegionLocation rl ON w.regLoc = rl.id"
            + " LEFT JOIN Location l ON l.id = rl.location.id GROUP BY l.id")
    List<WarehouseLocationCount> countWarehouseByLocation();

    /**
     * Get warehouse count by status
     *
     * @return
     */
    @Query("SELECT w.status AS status, COUNT(w.id) AS count FROM Warehouse w GROUP BY w.status")
    List<WarehouseStatusCount> countWarehouseByStatus();
}
