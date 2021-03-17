package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Warehouse Cad model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_cad")
public class WarehouseCad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "book_number")
    @NotNull
    @Min(value = 1)
    private Integer bookNumber;

    @Column(name = "serial_number")
    @NotNull
    @Min(value = 1)
    private Integer serialNumber;

    @Column(name = "used")
    @NotNull
    private Boolean used;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return bookNumber + "-" + serialNumber;
    }
}
