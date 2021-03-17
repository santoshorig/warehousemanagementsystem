package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Add On Service model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "add_on_service")
public class AddOnService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "unit_of_measure")
    @NotNull
    @Size(min = 2, max = 255)
    private String unitOfMeasure;

    @Column(name = "per_day_price")
    @NotNull
    private Boolean perDayPrice;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " - " + unitOfMeasure;
    }
}
