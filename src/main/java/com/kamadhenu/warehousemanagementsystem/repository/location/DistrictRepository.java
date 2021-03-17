package com.kamadhenu.warehousemanagementsystem.repository.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * District repository class
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    /**
     * Get all district by state
     *
     * @param state
     * @return
     */
    List<District> getByState(State state);
}
