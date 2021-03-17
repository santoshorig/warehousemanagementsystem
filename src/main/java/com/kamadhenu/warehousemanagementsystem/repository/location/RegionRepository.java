package com.kamadhenu.warehousemanagementsystem.repository.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Region repository class
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
