package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Head Office model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "head_office")
public class HeadOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "contact_title")
    @NotNull
    @Size(min = 2, max = 255)
    private String contactTitle;

    @Column(name = "contact_first_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String contactFirstName;

    @Column(name = "contact_middle_name")
    @Size(min = 0, max = 255)
    private String contactMiddleName;

    @Column(name = "contact_last_name")
    @Size(min = 2, max = 255)
    private String contactLastName;

    @Column(name = "contact_designation")
    @Size(min = 2, max = 255)
    private String contactDesignation;

    @Column(name = "contact_mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String contactMobileNumber;

    @Column(name = "contact_email")
    @Email
    @Size(min = 2, max = 255)
    private String contactEmail;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "district")
    private District district;
}
