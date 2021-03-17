package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * LR Inward Truck Bag model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "lr_inward_truck_bag")
public class LrInwardTruckBag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck_bag")
    private InwardTruckBag inwardTruckBag;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "lr")
    private Lr lr;
}
