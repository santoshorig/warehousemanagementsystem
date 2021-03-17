package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Inward Truck Bag model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "inward_truck_bag")
public class InwardTruckBag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "calculated_weight")
    @NotNull
    @Min(value = 0)
    private Double calculatedWeight;

    @Column(name = "manual_weight")
    @Min(value = 0)
    private Double manualWeight;

    @Column(name = "external_qc")
    @NotNull
    private Boolean externalQc;

    @Column(name = "lien")
    @NotNull
    private Boolean lien;

    @Column(name = "do")
    @NotNull
    private Boolean doModel;

    @Column(name = "outward")
    @NotNull
    private Boolean outward;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bag_type")
    private BagType bagType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_stack")
    private WarehouseStack warehouseStack;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck")
    private InwardTruck inwardTruck;

    /**
     * Get bag weight
     *
     * @return
     */
    public Double getWeight() {
        return manualWeight != null ? manualWeight : calculatedWeight;
    }
}
