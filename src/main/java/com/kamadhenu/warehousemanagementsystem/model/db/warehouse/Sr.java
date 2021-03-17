package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
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
import java.util.Objects;

/**
 * SR model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "sr")
public class Sr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sr_number")
    @NotNull
    @Size(min = 10, max = 15)
    @Pattern(regexp = ".*(^[0-9]{10,15}$)")
    private String srNumber;
    
    @Column(name = "sr_code")
    @Size(min = 0, max = 15)
    private String code;

    @Column(name = "sr_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date srDate;

    @Column(name = "sr_revalidation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date reValidationDate;

    @Column(name = "spot_price")
    @NotNull
    @Min(value = 0)
    private Double spotPrice;
    
    @Column(name = "system_spot_price")
    @Min(value = 0)
    private Double systemSpotPrice;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;
    
    @Column(name = "sr_validaty_from_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validityFromDate;
    
    @Column(name = "sr_validaty_To_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validityToDate;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_branch")
    private BankBranch bankBranch;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

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
      if (!Objects.isNull(bankBranch)) {
        return "SR: " + srNumber + " / " + bankBranch.getFriendlyName() + " / " + contract.getFriendlyName();
      } else {
        return "SR: " + srNumber + " / " + contract.getFriendlyName();
      }
    }
}
