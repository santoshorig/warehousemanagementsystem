package com.kamadhenu.warehousemanagementsystem.service.db.revision;

import com.kamadhenu.warehousemanagementsystem.model.db.revision.CustomRevision;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Custom revision listener
 */
@Service("customRevisionListenerService")
public class CustomRevisionListener implements RevisionListener {

    /**
     * Custom new revision mapper
     *
     * @param revision
     */
    public void newRevision(Object revision) {
        CustomRevision customRevision = (CustomRevision) revision;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            customRevision.setUser(user);
        }
    }
}
