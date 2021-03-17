package com.kamadhenu.warehousemanagementsystem.model.db.location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Location model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "pin_code")
    @NotNull
    @Min(value = 100000)
    @Max(value = 999999)
    private Integer pinCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "district")
    private District district;

    @NotAudited
    @ManyToMany(mappedBy = "location", fetch = FetchType.EAGER)
    private Set<Region> region;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " ( " + pinCode + " ) " + " - " + district.getName() + " - " + district.getState().getName();
    }

    @Override
    public String toString() {
        return getFriendlyName();
    }

    /**
     * Has geo codes
     *
     * @return
     */
    public Boolean hasGeoCodes() {
        return latitude != null && longitude != null;
    }
}
