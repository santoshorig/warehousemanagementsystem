package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Inward Truck model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "inward_truck")
public class InwardTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vehicle_number")
    @Size(min = 0, max = 255)
    private String vehicleNumber;

    @Column(name = "lot_number")
    @Size(min = 0, max = 255)
    private String lotNumber;

    @Column(name = "crop_year")
    @Size(min = 0, max = 255)
    private String cropYear;

    @Column(name = "dumping_date")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dumpingDate;

    @Column(name = "total_bags_bales_drums")
    @NotNull
    @Min(value = 1)
    private Integer totalBagsBalesDrums;

    @Column(name = "gross_weight")
    private Double grossWeight;

    @Column(name = "tare_weight")
    private Double tareWeight;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "gate_pass_document")
    private Document gatePassDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weighment_document")
    private Document weighmentDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "mandi_receipt_document")
    private Document mandiReceiptDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_check_document")
    private Document qualityCheckDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weighbridge")
    private Weighbridge weighbridge;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_lab")
    private QualityLab qualityLab;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "assayer")
    private Employee employee;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward")
    private Inward inward;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        String friendlyName = vehicleNumber;
        if (lotNumber != null) {
            friendlyName += " - " + lotNumber;
        }
        return friendlyName + " - " + totalBagsBalesDrums + " bags/bales/drums " + " - " + grossWeight + " (MT) " + " - " + inward
                .getFriendlyName();
    }

    /**
     * Has weighbridge
     *
     * @return
     */
    public Boolean hasWeighbridge() {
        return weighbridge != null;
    }

    /**
     * Has lot number
     *
     * @return
     */
    public Boolean hasLotNumber() {
        return lotNumber != null;
    }
}
