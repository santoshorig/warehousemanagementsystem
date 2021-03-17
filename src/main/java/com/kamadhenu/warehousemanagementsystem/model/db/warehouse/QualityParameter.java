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
 * Quality Parameter model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "quality_parameter")
public class QualityParameter {

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

    @Column(name = "mandatory")
    @NotNull
    private Boolean mandatory;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " (" + unitOfMeasure + ")";
    }
}
