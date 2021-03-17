package com.kamadhenu.warehousemanagementsystem.controller.db.general;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.service.db.general.NotificationService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Optional;

/**
 * <h1>Notification controller</h1>
 * <p>
 * This notification controller class provides the CRUD actions for notifications
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/notification")
public class NotificationController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Set notification status to read
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"/update-status/{id}/{checked}"}, method = RequestMethod.GET)
    public ResponseEntity updateStatus(@PathVariable Integer id, @PathVariable Boolean checked) {
        LOGGER.info("Notification update action called");
        Optional<Notification> notification = notificationService.get(id);
        Boolean updated = false;
        if (notification.isPresent()) {
            Notification existingNotification = notification.get();
            String status = checked ? constantService.getNOTIFICATION_STATUS().get("read") : constantService
                    .getNOTIFICATION_STATUS().get("unread");
            Date now = new Date();
            User user = helperService.getLoggedInDbUser();
            existingNotification.setStatus(status);
            existingNotification.setReadDate(now);
            existingNotification.setReadBy(user);
            notificationService.edit(existingNotification);
            updated = true;
        }
        return new ResponseEntity(updated, HttpStatus.OK);
    }
}
