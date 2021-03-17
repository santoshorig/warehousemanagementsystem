package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Warehouse Stack model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_stack")
public class WarehouseStack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "shed")
    @NotNull
    @Size(min = 1, max = 255)
    private String shed;

    @Column(name = "chamber")
    @NotNull
    @Size(min = 1, max = 255)
    private String chamber;

    @Column(name = "stack")
    @NotNull
    @Size(min = 1, max = 255)
    private String stack;

    @Column(name = "length")
    @Min(value = 0)
    private Double length;

    @Column(name = "breadth")
    @Min(value = 0)
    private Double breadth;

    @Column(name = "height")
    @Min(value = 0)
    private Double height;

    @Column(name = "full")
    @NotNull
    private Boolean full;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get area
     *
     * @return
     */
    public Double getArea() {
        return length * breadth;
    }

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return shed + "-" + chamber + "-" + stack;
    }
}
