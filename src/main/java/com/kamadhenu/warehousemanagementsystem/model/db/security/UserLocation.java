package com.kamadhenu.warehousemanagementsystem.model.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * User Location model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "user_location")
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;
}
