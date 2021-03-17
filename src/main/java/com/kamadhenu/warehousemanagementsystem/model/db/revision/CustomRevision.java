package com.kamadhenu.warehousemanagementsystem.model.db.revision;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.service.db.revision.CustomRevisionListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;

/**
 * Custom Revision model
 */
@Entity
@Data
@ToString
@EqualsAndHashCode
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "custom_rev_info")
public class CustomRevision extends DefaultRevisionEntity {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

}
