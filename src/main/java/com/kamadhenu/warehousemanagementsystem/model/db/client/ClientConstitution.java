package com.kamadhenu.warehousemanagementsystem.model.db.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Client Constitution model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode
@Table(name = "client_constitution")
public class ClientConstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "rank")
    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    private Integer rank;

    @Column(name = "partnership_label")
    @NotNull
    @Size(min = 2, max = 255)
    private String partnershipLabel;

    @Column(name = "minimum_partners")
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer minimumPartners;
}
