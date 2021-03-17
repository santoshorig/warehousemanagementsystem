package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Warehouse Weighbridge model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_weighbridge")
public class WarehouseWeighbridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "location")
    @NotNull
    @Size(min = 2, max = 255)
    private String location;

    @Column(name = "type")
    @NotNull
    @Size(min = 2, max = 255)
    private String type;

    @Column(name = "distance_from_warehouse")
    @Min(value = 0)
    private Double distanceFromWarehouse;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "weighbridge")
    private Weighbridge weighbridge;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;
}
