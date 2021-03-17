package com.kamadhenu.warehousemanagementsystem.repository.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notification repository class
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    /**
     * Find notification by role and status
     *
     * @param role
     * @param status
     * @return
     */
    List<Notification> findByRoleAndStatus(String role, String status);

    /**
     * Find notification by user and status
     *
     * @param user
     * @param status
     * @return
     */
    List<Notification> findByUserAndStatus(User user, String status);

}
