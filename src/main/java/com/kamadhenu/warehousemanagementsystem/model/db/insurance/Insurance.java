package com.kamadhenu.warehousemanagementsystem.model.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * Insurance model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "policy_number")
    @NotNull
    @Size(min = 2, max = 255)
    private String policyNumber;

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

    @Column(name = "sum_insured")
    @Min(value = 0)
    private Integer sumInsured;

    @Column(name = "premium_amount")
    @Min(value = 0)
    private Integer premiumAmount;

    @Column(name = "threshold")
    @Min(value = 0)
    private Integer threshold;

    @Column(name = "insurer")
    @NotNull
    @Size(min = 2, max = 255)
    private String insurer;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_type")
    private PolicyType policyType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_owner")
    private PolicyOwner policyOwner;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return policyNumber + " - " + policyType.getName() + " - " + policyOwner.getName();
    }
}
