package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Warehouse Commodity model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "warehouse_commodity")
public class WarehouseCommodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "planned_utilization")
    @NotNull
    @Min(value = 0)
    private Integer plannedUtilization;

    @Column(name = "price_per_mt")
    @NotNull
    @Min(value = 0)
    private Double pricePerMt;

    @Column(name = "funding_percentage")
    @NotNull
    @Min(value = 0)
    private Double fundingPercentage;

    @Column(name = "quality_check_by")
    @NotNull
    @Size(min = 2, max = 255)
    private String qualityCheckBy;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_lab")
    private QualityLab qualityLab;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodity")
    private Commodity commodity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;
}
