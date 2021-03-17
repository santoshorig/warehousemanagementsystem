package com.kamadhenu.warehousemanagementsystem.model.db.location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Region Location model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "region_location")
public class RegionLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "region")
    private Region region;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return region.getName() + " - " + location.getFriendlyName();
    }

    /**
     * Get full address of region location
     *
     * @return
     */
    public String getFullAddress() {
        return location.getName() + ", " + location
                .getPinCode() + " - " + location.getDistrict()
                .getName() + " - " + location.getDistrict().getState().getName() + " ( " + region.getName() + " )";
    }

    @Override
    public String toString() {
        return "RegionLocation{" +
                "id=" + id +
                '}';
    }
}
