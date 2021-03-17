package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Outward Truck Bag model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "outward_truck_bag")
public class OutwardTruckBag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do_inward_truck_bag")
    private DoInwardTruckBag doInwardTruckBag;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "outward_truck")
    private OutwardTruck outwardTruck;
}
