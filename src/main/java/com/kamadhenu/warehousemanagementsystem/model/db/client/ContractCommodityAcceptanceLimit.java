package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Contract Commodity Acceptance Limit model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "contract_commodity_acceptance_limit")
public class ContractCommodityAcceptanceLimit {

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

    @Column(name = "test_result_validation")
    @NotNull
    private Boolean testResultValidation;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_parameter")
    private QualityParameter qualityParameter;

    @Column(name = "contract_commodity")
    private Integer contractCommodity;
}
