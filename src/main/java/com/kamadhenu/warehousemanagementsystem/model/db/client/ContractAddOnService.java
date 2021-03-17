package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Contract Add On Service model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "contract_add_on_service")
public class ContractAddOnService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    @NotNull
    @Min(value = 0)
    private Double price;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "add_on_service")
    private AddOnService addOnService;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "contract")
    private Contract contract;
}
