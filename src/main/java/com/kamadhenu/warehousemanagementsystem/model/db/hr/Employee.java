package com.kamadhenu.warehousemanagementsystem.model.db.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Employee model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = ".*(^OCI[0-9]{5}$)")
    private String code;

    @Column(name = "title")
    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String lastName;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "signature")
    private Document signature;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "reporting_to")
    private Employee reportingTo;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_position")
    private EmployeePosition employeePosition;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    /**
     * Get full name of employee
     *
     * @return
     */
    public String getName() {
        return title + " " + firstName + " " + lastName;
    }

    /**
     * Get full name and code of employee
     *
     * @return
     */
    public String getNameAndCode() {
        return getName() + " - " + code;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active=" + active +
                '}';
    }
}
