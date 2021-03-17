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
 * Outward Truck Quality Check model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "outward_truck_quality_check")
public class OutwardTruckQualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "test_result")
    @NotNull
    @Min(value = 0)
    private Double testResult;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_parameter")
    private QualityParameter qualityParameter;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "outward_truck")
    private OutwardTruck outwardTruck;

}
