package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Warehouse Closure model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_closure")
public class WarehouseClosure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "closure_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date closureDate;

    @Column(name = "status")
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "dehire_letter")
    private Document dehireLetter;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;
}
