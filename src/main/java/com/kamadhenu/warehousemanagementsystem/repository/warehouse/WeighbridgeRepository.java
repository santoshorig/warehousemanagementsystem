package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Weighbridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Weighbridge repository class
 */
@Repository
public interface WeighbridgeRepository extends JpaRepository<Weighbridge, Integer> {

    /**
     * Find by location
     *
     * @param location
     * @return
     */
    List<Weighbridge> findByLocation(Location location);

}
