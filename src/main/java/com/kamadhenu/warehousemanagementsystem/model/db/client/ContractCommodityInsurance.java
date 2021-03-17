package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Contract Commodity Insurance Limit model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "contract_commodity_insurance")
public class ContractCommodityInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "insurance_owner")
    @NotNull
    @Size(min = 2, max = 255)
    private String insuranceOwner;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance")
    private Insurance insurance;

    @Column(name = "contract_commodity")
    private Integer contractCommodity;
}
