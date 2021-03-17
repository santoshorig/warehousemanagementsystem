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
 * DO Inward Truck Bag model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "do_inward_truck_bag")
public class DoInwardTruckBag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "calculated_weight")
    @NotNull
    @Min(value = 0)
    private Double calculatedWeight;

    @Column(name = "outward")
    @NotNull
    private Boolean outward;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck_bag")
    private InwardTruckBag inwardTruckBag;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do")
    private Do doModel;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return id + " - " + calculatedWeight + " (Kg)";
    }
}
