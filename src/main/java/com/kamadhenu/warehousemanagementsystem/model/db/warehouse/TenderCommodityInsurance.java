package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Tender Commodity Insurance Limit model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "tender_commodity_insurance")
public class TenderCommodityInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance")
    private Insurance insurance;

    @Column(name = "tender_commodity")
    private Integer tenderCommodity;
}
