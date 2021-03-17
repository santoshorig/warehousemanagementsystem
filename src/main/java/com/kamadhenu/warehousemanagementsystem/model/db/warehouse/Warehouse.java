package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Warehouse model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @NotNull
    @Size(min = 2, max = 255)
    private String code;

    @Column(name = "erp_code")
    @Size(min = 0, max = 255)
    private String erpCode;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

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

    @Column(name = "contact_person_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String contactPersonName;

    @Column(name = "contact_person_mobile")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String contactPersonMobile;

    @Column(name = "letter_reference_number")
    @Size(min = 0, max = 255)
    private String letterReferenceNumber;

    @Column(name = "letter_reference_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date letterReferenceDate;

    @Column(name = "last_rework")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastRework;

    @Column(name = "first_sent_to_risk_assessment")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date firstSentToRiskAssessment;

    @Column(name = "first_sent_to_risk_approver")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date firstSentToRiskApprover;

    @Column(name = "first_sent_to_management")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date firstSentToManagement;

    @Column(name = "last_sent_to_management")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastSentToManagement;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @Column(name = "status")
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "term_sheet_document")
    private Document termSheetDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inspection_by")
    private Employee inspectionBy;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tender")
    private Tender tender;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_type")
    private WarehouseType warehouseType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "takeover_type")
    private TakeoverType takeoverType;

    ///TODO FIX THIS PROPERTY FOR RELATIONSHIP
    @Column(name = "reg_loc")
    @NotNull
    private Integer regLoc;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Get full address of warehouse
     *
     * @return
     */
    public String getFullAddress() {
        return addressLine1 + " " + addressLine2 + " " + addressLine3;
    }

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return "Warehouse Name:" + name + " Warehouse Code: " + code;
    }
}
