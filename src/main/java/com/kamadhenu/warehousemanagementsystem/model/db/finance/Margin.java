package com.kamadhenu.warehousemanagementsystem.model.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Margin model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "margin")
public class Margin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "margin")
    @NotNull
    @Min(value = 0)
    private Double margin;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodity")
    private Commodity commodity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "finance_entity")
    private FinanceEntity financeEntity;
}
