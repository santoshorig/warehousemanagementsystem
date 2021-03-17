package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.validator.BlankOrPatternConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Client model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @NotNull
    @Size(min = 2, max = 255)
    private String code;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String mobileNumber;

    @Column(name = "landline_number")
    @Size(min = 0, max = 255)
    private String landlineNumber;

    @Column(name = "email")
    @Email
    @Size(min = 0, max = 255)
    private String email;

    @Column(name = "pan_number")
    @Size(min = 0, max = 10)
    @BlankOrPatternConstraint(regexp = ".*(^[A-Za-z]{5}\\d{4}[A-Za-z]{1}$)")
    private String panNumber;

    @Column(name = "gst_number")
    @Size(min = 0, max = 15)
    @BlankOrPatternConstraint(regexp = ".*(^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$)")
    private String gstNumber;

    @Column(name = "date_of_incorporation")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateOfIncorporation;

    @Column(name = "politically_involved")
    @NotNull
    private Boolean politicallyInvolved;

    @Column(name = "has_government_business")
    @NotNull
    private Boolean hasGovernmentBusiness;

    @Column(name = "funding_eligible")
    @NotNull
    private Boolean fundingEligible;

    @Column(name = "status")
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_constitution")
    private ClientConstitution clientConstitution;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_type")
    private ClientType clientType;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "sourced_by_employee")
    private Employee sourcedByEmployee;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "sourced_by_bank")
    private Bank sourcedByBank;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "related_client")
    private Client relatedClient;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "client_commodity", joinColumns = @JoinColumn(name = "client", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "commodity", referencedColumnName = "id"))
    private Set<Commodity> commodity;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private List<ClientAddress> clientAddresses;

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return "Client Name: " + name + " Client Code: " + code;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Client [id=%d, name='%s']%n",
                id, name
        );
        if (commodity != null) {
            for (Commodity commodity : commodity) {
                result += String.format(
                        "Commodity[id=%d, name='%s']%n",
                        commodity.getId(), commodity.getName()
                );
            }
        }
        if (clientAddresses != null) {
            for (ClientAddress clientAddress : clientAddresses) {
                result += String.format(
                        "ClientAddress[id=%d, addressType='%s']%n",
                        clientAddress.getId(), clientAddress.getAddressType()
                );
            }
        }
        return result;
    }
}
