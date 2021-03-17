package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Warehouse Detail model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_detail")
public class WarehouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "construction_year")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date constructionYear;

    @Column(name = "license")
    @NotNull
    @Size(min = 2, max = 255)
    private String license;

    @Column(name = "agreement_type")
    @NotNull
    @Size(min = 2, max = 255)
    private String agreementType;

    @Column(name = "length")
    @NotNull
    @Min(value = 0)
    private Double length;

    @Column(name = "breadth")
    @NotNull
    @Min(value = 0)
    private Double breadth;

    @Column(name = "height")
    @NotNull
    @Min(value = 0)
    private Double height;

    @Column(name = "shape")
    @NotNull
    @Size(min = 2, max = 255)
    private String shape;

    @Column(name = "license_number")
    @Size(min = 0, max = 255)
    private String licenseNumber;

    @Column(name = "validity_till")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validityTill;

    @Column(name = "issuing_authority")
    @Size(min = 0, max = 255)
    private String issuingAuthority;

    @Column(name = "accreditation")
    @Size(min = 0, max = 255)
    private String accreditation;

    @Column(name = "fssai_license_number")
    @Size(min = 0, max = 255)
    private String fssaiLicenseNumber;

    @Column(name = "location_type")
    @NotNull
    @Size(min = 2, max = 255)
    private String locationType;

    @Column(name = "functional_factory")
    private Boolean functionalFactory;

    @Column(name = "closed_months")
    private Integer closedMonths;

    @Column(name = "same_entry_factory")
    private Boolean sameEntryFactory;

    @Column(name = "competition")
    @Size(min = 0, max = 255)
    private String competition;

    @Column(name = "rent")
    @NotNull
    @Min(value = 0)
    private Double rent;

    @Column(name = "rent_per_mt")
    @Min(value = 0)
    private Double rentPerMt;

    @Column(name = "rent_owner_percentage")
    @Min(value = 0)
    @Max(value = 100)
    private Integer rentOwnerPercentage;

    @Column(name = "gst_applicable")
    @NotNull
    private Boolean gstApplicable;

    @Column(name = "structure_insurance")
    @NotNull
    private Boolean structureInsurance;

    @Column(name = "structure_insurance_expiry")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date structureInsuranceExpiry;

    @Column(name = "agreement_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date agreementStart;

    @Column(name = "agreement_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date agreementEnd;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get area
     *
     * @return
     */
    public Double getArea() {
        return length * breadth;
    }

    /**
     * Get friendly dimensions
     *
     * @return
     */
    public String getFriendlyDimensions() {
        return length.toString() + " * " + breadth.toString() + " * " + height.toString();
    }
}
