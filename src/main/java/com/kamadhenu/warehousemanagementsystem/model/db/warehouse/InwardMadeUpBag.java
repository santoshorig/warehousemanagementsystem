package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Inward Made Up Bag model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "inward_made_up_bag")
public class InwardMadeUpBag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "outward")
    @NotNull
    private Boolean outward;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_stack")
    private WarehouseStack warehouseStack;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward")
    private Inward inward;

}
