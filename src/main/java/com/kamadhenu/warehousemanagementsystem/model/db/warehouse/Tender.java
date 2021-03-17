package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

/**
 * Tender model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "tender")
public class Tender {

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
    @Size(min = 2)
    private String name;

    @Column(name = "tender_date")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date tenderDate;

    @Column(name = "capacity_covered")
    @NotNull
    @Min(value = 0)
    private Double capacityCovered;

    @Column(name = "effective_from")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date effectiveFrom;

    @Column(name = "effective_to")
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date effectiveTo;

    @Column(name = "email")
    @NotNull
    @Email
    @Size(min = 2, max = 255)
    private String email;

    @Column(name = "mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String mobileNumber;

    @Column(name = "storage_charge")
    @NotNull
    @Min(value = 0)
    private Double storageCharge;

    @Column(name = "storage_charge_basis")
    @NotNull
    @Size(min = 2, max = 255)
    private String storageChargeBasis;

    @Column(name = "lock_in")
    @NotNull
    private Boolean lockIn;

    @Column(name = "lock_in_quantity")
    @Min(value = 0)
    private Double lockInQuantity;

    @Column(name = "lock_in_unit")
    @Size(min = 0, max = 255)
    private String lockInUnit;

    @Column(name = "lock_in_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lockInEndDate;

    @Column(name = "billing_peak_stock")
    @Size(min = 0, max = 255)
    private String billingPeakStock;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "head_office")
    private HeadOffice headOffice;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "basis_of_takeover")
    private BasisOfTakeover basisOfTakeover;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "tender_client")
    private TenderClient tenderClient;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "tender_material_owner", joinColumns = @JoinColumn(name = "tender", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "material_owner", referencedColumnName = "id"))
    private Set<MaterialOwner> materialOwner;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "tender_commodity", joinColumns = @JoinColumn(name = "tender", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "commodity", referencedColumnName = "id"))
    private Set<Commodity> commodity;

    @Override
    public String toString() {
        String result = String.format(
                "Tender [id=%d, code='%s']%n",
                id, code
        );
        if (materialOwner != null) {
            for (MaterialOwner materialOwner : materialOwner) {
                result += String.format(
                        "MaterialOwner[id=%d, name='%s']%n",
                        materialOwner.getId(), materialOwner.getName()
                );
            }
        }
        if (commodity != null) {
            for (Commodity commodity : commodity) {
                result += String.format(
                        "Commodity[id=%d, name='%s']%n",
                        commodity.getId(), commodity.getName()
                );
            }
        }
        return result;
    }
}
