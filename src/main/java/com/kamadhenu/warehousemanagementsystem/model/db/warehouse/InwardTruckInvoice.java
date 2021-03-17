package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Inward Truck Invoice model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "inward_truck_invoice")
public class InwardTruckInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "invoice_number")
    @NotNull
    @Size(min = 2, max = 255)
    private String invoiceNumber;

    @Column(name = "invoice_date")
    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "commodity_price")
    @NotNull
    @Min(value = 1)
    private Double commodityPrice;

    @Column(name = "gst")
    @NotNull
    @Min(value = 0)
    private Double gst;

    @Column(name = "invoice_value")
    @NotNull
    @Min(value = 1)
    private Double invoiceValue;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_document")
    private Document invoiceDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inward_truck")
    private InwardTruck inwardTruck;

}
