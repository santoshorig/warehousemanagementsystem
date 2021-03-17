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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * DO model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "do")
public class Do {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "do_number")
    @NotNull
    @Size(min = 10, max = 15)
    @Pattern(regexp = ".*(^[0-9]{10,15}$)")
    private String doNumber;

    @Column(name = "do_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date doDate;

    @Column(name = "do_weight")
    @NotNull
    @Min(value = 0)
    private Double doWeight;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @Column(name = "outward")
    @NotNull
    private Boolean outward;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do_letter_document")
    private Document doLetterDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do_email_document")
    private Document doEmailDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "do_kyc_document")
    private Document doKycDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "contract")
    private Contract contract;

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return "DO: " + doNumber + "DO Weight (MT): " + doWeight + " / " + contract.getFriendlyName();
    }

}
