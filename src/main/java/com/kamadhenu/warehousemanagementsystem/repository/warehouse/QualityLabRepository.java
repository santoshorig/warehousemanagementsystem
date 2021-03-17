package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Quality Lab repository class
 */
@Repository
public interface QualityLabRepository extends JpaRepository<QualityLab, Integer> {

    /**
     * Get quality lab by location
     *
     * @param location
     * @return
     */
    List<QualityLab> getByLocation(Location location);
}
