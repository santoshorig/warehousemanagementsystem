package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Client Bank model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "client_bank")
public class ClientBank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_number")
    @Size(min = 0, max = 255)
    private String accountNumber;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_branch")
    private BankBranch bankBranch;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank")
    private Bank bank;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;
}
