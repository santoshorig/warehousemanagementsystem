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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * LR model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "lr")
public class Lr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lr_number")
    @NotNull
    @Size(min = 10, max = 15)
    @Pattern(regexp = ".*(^[0-9]{10,15}$)")
    private String lrNumber;

    @Column(name = "lr_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lrDate;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ro_document")
    private Document roDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ro_email_document")
    private Document roEmailDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sr")
    private Sr sr;

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return "LR: " + lrNumber + " / " + sr.getFriendlyName();
    }

}
