package com.kamadhenu.warehousemanagementsystem.model.db.bank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bank model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "srwr_prepend_text")
    @Size(min = 0, max = 255)
    private String srWrPrependText;

    @Column(name = "srwr_append_text")
    @Size(min = 0, max = 255)
    private String srWrAppendText;

    @Column(name = "allow_sr")
    @NotNull
    private Boolean allowSr;

    @Column(name = "allow_wr")
    @NotNull
    private Boolean allowWr;
}
