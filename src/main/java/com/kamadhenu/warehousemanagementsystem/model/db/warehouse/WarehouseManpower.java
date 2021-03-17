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
 * Warehouse Manpower model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_manpower")
public class WarehouseManpower {

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

    @Column(name = "new_hire")
    @NotNull
    @Min(value = 0)
    private Integer newHire;

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
    @JoinColumn(name = "manpower")
    private Manpower manpower;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "roaming_warehouse")
    private Warehouse roamingWarehouse;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Is roaming manpower
     *
     * @return
     */
    public Boolean isRoaming() {
        return roamingWarehouse != null;
    }

    /**
     * Get total available
     *
     * @return
     */
    public Integer totalAvailable() {
        return availableQuantity + newHire + thirdPartyTransferred + otherWarehouseTransferred;
    }
}
