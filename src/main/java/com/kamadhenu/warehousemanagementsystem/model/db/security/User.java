package com.kamadhenu.warehousemanagementsystem.model.db.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * User model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    @NotNull
    @Email
    @Size(min = 2, max = 255)
    private String email;

    @Column(name = "password")
    @NotNull
    @Size(min = 0, max = 255)
    private String password;

    @Column(name = "role")
    @NotNull
    @Size(min = 2, max = 255)
    private String role;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee")
    private Employee employee;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_location", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "location", referencedColumnName = "id"))
    private Set<Location> location;

    /**
     * Return user
     */
    public User() {

    }

    /**
     * Set user
     *
     * @param user
     */
    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.role = user.getRole();
        this.employee = user.getEmployee();
    }

    @Override
    public String toString() {
        String result = String.format(
                "User [id=%d, email='%s']%n",
                id, email
        );
        if (location != null) {
            for (Location location : location) {
                result += String.format(
                        "Location[id=%d, name='%s']%n",
                        location.getId(), location.getName()
                );
            }
        }
        return result;
    }
}
