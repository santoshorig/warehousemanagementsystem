package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Contract model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2)
    private String name;

    @Column(name = "contract_date")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date contractDate;

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

    @Column(name = "client_name_sr_wr")
    @NotNull
    @Size(min = 2, max = 255)
    private String clientNameSRWR;

    @Column(name = "warehouse_name_sr_wr")
    @NotNull
    @Size(min = 2, max = 255)
    private String warehouseNameSRWR;

    @Column(name = "billing_type")
    @Size(min = 0, max = 255)
    private String billingType;

    @Column(name = "post_lock_in_billing_charge")
    @Min(value = 0)
    private Double postLockInBillingCharge;

    @Column(name = "locked_inward")
    @NotNull
    private Boolean lockedInward;

    @Column(name = "locked_sr")
    @NotNull
    private Boolean lockedSr;

    @Column(name = "locked_do")
    @NotNull
    private Boolean lockedDo;

    @Column(name = "locked_outward")
    @NotNull
    private Boolean lockedOutward;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @Column(name = "status")
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tender")
    private Tender tender;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_contract")
    private Contract parentContract;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "basis_of_takeover")
    private BasisOfTakeover basisOfTakeover;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "contract_commodity", joinColumns = @JoinColumn(name = "contract", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "commodity", referencedColumnName = "id"))
    private Set<Commodity> commodity;

    @Override
    public String toString() {
        String result = String.format(
                "Contract [id=%d]%n",
                id
        );
        if (commodity != null) {
            for (Commodity c : commodity) {
                result += String.format(
                        "Commodity[id=%d, name='%s']%n",
                        c.getId(), c.getName()
                );
            }
        }
        return result;
    }

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return "Contract: " + id + " Commodity: " + getCommoditiesFriendlyName() + " / " + client.getFriendlyName() + " / " + warehouse
                .getFriendlyName();
    }

    /**
     * Get locked
     *
     * @return
     */
    public Boolean getLocked() {
        return lockedInward || lockedSr || lockedDo || lockedOutward;
    }

    /**
     * Get commodities friendly name
     *
     * @return
     */
    public String getCommoditiesFriendlyName() {
        List<String> commodities = new ArrayList<>();
        if (commodity != null) {
            for (Commodity c : commodity) {
                commodities.add(c.getFriendlyName());
            }
        }
        return String.join(" , ", commodities);
    }
}
