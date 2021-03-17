package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Warehouse Owner model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_owner")
public class WarehouseOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String firstName;

    @Column(name = "middle_name")
    @Size(min = 0, max = 255)
    private String middleName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String lastName;

    @Column(name = "mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String mobileNumber;

    @Column(name = "address_line_1")
    @NotNull
    @Size(min = 2, max = 255)
    private String addressLine1;

    @Column(name = "address_line_2")
    @Size(min = 0, max = 255)
    private String addressLine2;

    @Column(name = "address_line_3")
    @Size(min = 0, max = 255)
    private String addressLine3;

    @Column(name = "background")
    private String background;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_of_party")
    private StatusOfParty statusOfParty;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get full name of owner
     *
     * @return
     */
    public String getName() {

        String fullName = title + " " + firstName;
        if (middleName != null) {
            fullName = fullName + " " + middleName;
        }
        fullName = fullName + " " + lastName;

        return fullName;
    }

    /**
     * Get friendly details
     *
     * @return
     */
    public String getFriendlyDetails() {
        return getName() + " (" + statusOfParty.getName() + ")";
    }
}
