package com.kamadhenu.warehousemanagementsystem.model.db.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User Device model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "user_device")
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "device_details")
    @NotNull
    private String deviceDetails;

    @Column(name = "ip")
    @NotNull
    private String ip;

    @Column(name = "last_logged_in")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLoggedIn;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;
}
