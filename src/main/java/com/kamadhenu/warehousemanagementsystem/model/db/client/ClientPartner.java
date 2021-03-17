package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;
import com.kamadhenu.warehousemanagementsystem.validator.BlankOrPatternConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client Partner model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "client_partner")
public class ClientPartner {

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

    @Column(name = "fathers_name")
    @Size(min = 0, max = 255)
    private String fathersName;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(name = "mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String mobileNumber;

    @Column(name = "pan_number")
    @Size(min = 0, max = 10)
    @BlankOrPatternConstraint(regexp = ".*(^[A-Za-z]{5}\\d{4}[A-Za-z]{1}$)")
    private String panNumber;

    @Column(name = "aadhar_number")
    @Size(min = 0, max = 12)
    @BlankOrPatternConstraint(regexp = ".*(^[2-9]{1}[0-9]{11}$)")
    private String aadharNumber;

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

    @Column(name = "cibil_score")
    @Max(900)
    private Integer cibilScore;

    @Column(name = "cibil_comments")
    private String cibilComments;

    @Column(name = "highmark_score")
    @Max(900)
    private Integer highmarkScore;

    @Column(name = "highmark_comments")
    private String highmarkComments;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cibil_document")
    private Document cibilDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "highmark_document")
    private Document highmarkDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "highmark_risk_category")
    private HighmarkRiskCategory highmarkRiskCategory;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    /**
     * Get full name of partner
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
     * Get full address of client partner
     *
     * @return
     */
    public String getFullAddress() {
        return addressLine1 + " " + addressLine2 + " " + addressLine3 + " " + location.getName() + ", " + location
                .getPinCode() + " - " + location.getDistrict()
                .getName() + " - " + location.getDistrict().getState().getName();
    }

    /**
     * Get date of birth in human friendly format
     *
     * @return
     */
    public String getDOB() {
        String dob = null;
        if (dateOfBirth != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            dob = formatter.format(dateOfBirth);
        }
        return dob;
    }
}
