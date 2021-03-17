package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Quality Lab model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "quality_lab")
public class QualityLab {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "address_line_1")
    @NotNull
    @Size(min = 2, max = 255)
    private String addressLine1;

    @Column(name = "address_line_2")
    @Size(min = 0, max = 255)
    private String addressLine2;

    @Column(name = "address_line_3")
    @Size(min = 0, max = 255)
    private String addressLine3;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    /**
     * Get full address of quality lab
     *
     * @return
     */
    public String getFullAddress() {
        return addressLine1 + " " + addressLine2 + " " + addressLine3 + " " + location.getFriendlyName();
    }

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " " + getFullAddress();
    }
}
