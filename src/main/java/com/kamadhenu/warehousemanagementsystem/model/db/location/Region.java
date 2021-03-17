package com.kamadhenu.warehousemanagementsystem.model.db.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Region model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "business_type")
    private BusinessType businessType;

    @NotAudited
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "region_location", joinColumns = @JoinColumn(name = "region", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "location", referencedColumnName = "id"))
    private Set<Location> location;

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
