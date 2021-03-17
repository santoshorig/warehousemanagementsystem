package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.repository.general.NotificationRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ConstantService constantService;

    /**
     * Get notification
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Notification> get(Integer id) {
        return notificationRepository.findById(id);
    }

    /**
     * Edit notification
     *
     * @param Notification
     * @return
     */
    @Override
    public Notification edit(Notification Notification) {
        return notificationRepository.save(Notification);
    }

    /**
     * Delete notification
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Get all notification basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Notification> getAll(Integer pageNumber, Integer pageSize) {
        return notificationRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all notification
     *
     * @return
     */
    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    /**
     * Get notification count
     *
     * @return
     */
    public Long count() {
        return notificationRepository.count();
    }

    /**
     * Get unread notification by role
     *
     * @param role
     * @return
     */
    public List<Notification> getUnreadByRole(String role) {
        return notificationRepository.findByRoleAndStatus(role, constantService.getNOTIFICATION_STATUS().get("unread"));
    }

    /**
     * Get unread notification by user
     *
     * @param user
     * @return
     */
    public List<Notification> getUnreadByUser(User user) {
        return notificationRepository.findByUserAndStatus(user, constantService.getNOTIFICATION_STATUS().get("unread"));
    }
}
