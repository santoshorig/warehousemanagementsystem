package com.kamadhenu.warehousemanagementsystem.model.db.bank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Bank Branch model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "bank_branch")
public class BankBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "ifsc_code")
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = ".*(^[A-Za-z]{4}0[A-Z0-9a-z]{6}$)")
    private String ifscCode;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank")
    private Bank bank;

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return "Branch Name: " + name + " Bank IFSC: " + ifscCode + " Bank Name: " + bank.getName();
    }
}
