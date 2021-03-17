package com.kamadhenu.warehousemanagementsystem.repository.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User Device repository class
 */
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {

    /**
     * Find user device by user
     *
     * @param user
     * @return
     */
    List<UserDevice> findByUser(User user);
}
