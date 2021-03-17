package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Contract Commodity model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "contract_commodity")
public class ContractCommodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodity")
    private Commodity commodity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract")
    private Contract contract;
}
