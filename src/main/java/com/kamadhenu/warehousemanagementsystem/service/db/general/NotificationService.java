package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;

import java.util.List;
import java.util.Optional;

/**
 * Notification interface
 */
public interface NotificationService {

    /**
     * Get notification
     *
     * @param id
     * @return
     */
    Optional<Notification> get(Integer id);

    /**
     * Edit notification
     *
     * @param Notification
     * @return
     */
    Notification edit(Notification Notification);

    /**
     * Delete notification
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all notification basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Notification> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all notification
     *
     * @return
     */
    List<Notification> getAll();

    /**
     * Get notification count
     *
     * @return
     */
    Long count();

    /**
     * Get unread notification by role
     *
     * @param role
     * @return
     */
    List<Notification> getUnreadByRole(String role);

    /**
     * Get unread notification by user
     *
     * @param user
     * @return
     */
    List<Notification> getUnreadByUser(User user);
}
