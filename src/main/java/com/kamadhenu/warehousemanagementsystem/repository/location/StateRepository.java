package com.kamadhenu.warehousemanagementsystem.repository.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * State repository class
 */
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
