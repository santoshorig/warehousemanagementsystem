package com.kamadhenu.warehousemanagementsystem.repository.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User Location repository class
 */
@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Integer> {

    /**
     * Find user location by user
     *
     * @param user
     * @return
     */
    List<UserLocation> findByUser(User user);
}
