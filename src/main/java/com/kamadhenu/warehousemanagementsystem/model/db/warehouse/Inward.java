package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Inward model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "inward")
public class Inward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "inward_type")
    @NotNull
    @Size(min = 2, max = 255)
    private String inwardType;

    @Column(name = "cad_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date cadDate;

    @Column(name = "cdd_number")
    @Size(min = 0, max = 255)
    private String cddNumber;

    @Column(name = "cdd_date")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date cddDate;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cad_document")
    private Document cadDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cdd_document")
    private Document cddDocument;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "grn_document")
    private Document grnDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_cad")
    private WarehouseCad warehouseCad;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "contract")
    private Contract contract;

    @Column(name = "upload")
    @NotNull
    private Boolean upload;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return inwardType + "-" + contract.getFriendlyName();
    }

    /**
     * Get simple cad name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getSimpleCadName() {
        return "Inward: " + id.toString() + " Book Number: " + warehouseCad.getBookNumber()
                .toString() + " Serial Number: " + warehouseCad.getSerialNumber()
                .toString();
    }
}
