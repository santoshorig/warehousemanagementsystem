package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Inward Truck Quality Check model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "inward_truck_quality_check")
public class InwardTruckQualityCheck {

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

    @Column(name = "test_result_validation")
    @NotNull
    private Boolean testResultValidation;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_parameter")
    private QualityParameter qualityParameter;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck")
    private InwardTruck inwardTruck;

}
