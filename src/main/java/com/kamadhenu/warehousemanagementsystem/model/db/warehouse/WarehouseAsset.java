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
 * Warehouse Asset model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_asset")
public class WarehouseAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mtf_recommendation")
    @NotNull
    @Min(value = 0)
    private Integer mtfRecommendation;

    @Column(name = "available_quantity")
    @NotNull
    @Min(value = 0)
    private Integer availableQuantity;

    @Column(name = "procured")
    @NotNull
    @Min(value = 0)
    private Integer procured;

    @Column(name = "third_party_transferred")
    @NotNull
    @Min(value = 0)
    private Integer thirdPartyTransferred;

    @Column(name = "third_party_name")
    @Size(min = 0, max = 255)
    private String thirdPartyName;

    @Column(name = "other_warehouse_transferred")
    @NotNull
    @Min(value = 0)
    private Integer otherWarehouseTransferred;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "other_warehouse")
    private Warehouse otherWarehouse;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "asset")
    private Asset asset;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get total available
     *
     * @return
     */
    public Integer totalAvailable() {
        return availableQuantity + procured + thirdPartyTransferred + otherWarehouseTransferred;
    }
}
