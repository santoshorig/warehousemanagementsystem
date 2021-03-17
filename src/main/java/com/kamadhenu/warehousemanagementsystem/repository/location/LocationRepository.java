package com.kamadhenu.warehousemanagementsystem.repository.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Location repository class
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    /**
     * Get all locations by districts
     *
     * @param districtList
     * @return
     */
    List<Location> getByDistrictIn(List<District> districtList);
}
