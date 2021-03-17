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
 * Outward Truck model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "outward_truck")
public class OutwardTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vehicle_number")
    @Size(min = 0, max = 255)
    private String vehicleNumber;

    @Column(name = "total_bags_bales_drums")
    @NotNull
    @Min(value = 1)
    private Integer totalBagsBalesDrums;

    @Column(name = "gross_weight")
    private Double grossWeight;

    @Column(name = "tare_weight")
    private Double tareWeight;

    @Column(name = "loading_date")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date loadingDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "gate_pass_document")
    private Document gatePassDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weighment_document")
    private Document weighmentDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_check_document")
    private Document qualityCheckDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weighbridge")
    private Weighbridge weighbridge;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_lab")
    private QualityLab qualityLab;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "assayer")
    private Employee employee;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "outward")
    private Outward outward;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return vehicleNumber + " - " + totalBagsBalesDrums + " bags/bales/drums " + " - " + grossWeight + " (MT) " + " - " + outward
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
}
