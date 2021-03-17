package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

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
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Outward model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "outward")
public class Outward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "outward_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date outwardDate;

    @Column(name = "cdf_number")
    @NotNull
    @Size(min = 2, max = 255)
    private String cdfNumber;

    @Column(name = "cdf_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date cdfDate;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cdf_document")
    private Document cdfDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do")
    private Do doModel;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return doModel.getFriendlyName();
    }
}
