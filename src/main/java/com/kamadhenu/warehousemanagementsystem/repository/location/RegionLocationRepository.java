package com.kamadhenu.warehousemanagementsystem.repository.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Region Location repository class
 */
@Repository
public interface RegionLocationRepository extends JpaRepository<RegionLocation, Integer> {

}
