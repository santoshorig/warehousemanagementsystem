package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * SR Inward Truck Quality Check model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "sr_inward_truck_quality_check")
public class SrInwardTruckQualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "min_limit")
    @NotNull
    @Min(value = 0)
    private Double minLimit;

    @Column(name = "max_limit")
    @NotNull
    @Min(value = 0)
    private Double maxLimit;

    @Column(name = "test_result")
    @NotNull
    @Min(value = 0)
    private Double testResult;
    
    @Column(name = "sr_qc_test_result")
    @NotNull
    @Min(value = 0)
    private Double srQcTestResult;

    @Column(name = "validation_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validationDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_parameter")
    private QualityParameter qualityParameter;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck")
    private InwardTruck inwardTruck;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sr")
    private Sr sr;

}
