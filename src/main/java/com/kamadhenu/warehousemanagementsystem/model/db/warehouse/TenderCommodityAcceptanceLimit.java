package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Tender Commodity Acceptance Limit model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "tender_commodity_acceptance_limit")
public class TenderCommodityAcceptanceLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "min_limit")
    @NotNull
    @Min(value = 0)
    private Double minLimit;

    @Column(name = "max_limit")
    @NotNull
    @Min(value = 0)
    private Double maxLimit;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_parameter")
    private QualityParameter qualityParameter;

    @Column(name = "tender_commodity")
    private Integer tenderCommodity;
}
