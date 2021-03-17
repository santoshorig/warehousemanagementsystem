package com.kamadhenu.warehousemanagementsystem.model.db.general;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BagType model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "bag_type")
public class BagType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "capacity")
    @NotNull
    @Min(value = 0)
    private Double capacity;

    @Column(name = "empty_weight")
    @NotNull
    @Min(value = 0)
    private Double emptyWeight;

    /**
     * Get actual weight
     *
     * @return
     */
    public Double getActualWeight() {
        return capacity - emptyWeight;
    }

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " ( " + capacity + " - " + emptyWeight + " )";
    }
}
