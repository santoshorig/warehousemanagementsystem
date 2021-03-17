package com.kamadhenu.warehousemanagementsystem.model.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Notification model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "notification")
    @NotNull
    @Size(min = 2)
    private String notification;

    @Column(name = "notification_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date notificationDate;

    @Column(name = "read_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date readDate;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "read_by")
    private User readBy;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @Column(name = "role")
    @NotNull
    @Size(min = 2, max = 255)
    private String role;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;
}
