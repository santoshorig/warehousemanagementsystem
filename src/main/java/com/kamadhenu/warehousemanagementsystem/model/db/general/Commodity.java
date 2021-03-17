package com.kamadhenu.warehousemanagementsystem.model.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Commodity model
 */
@Entity
@Audited
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "commodity")
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(name = "unit_of_measure")
    @NotNull
    @Size(min = 2, max = 255)
    private String unitOfMeasure;

    @Column(name = "storage_in")
    @NotNull
    @Size(min = 2, max = 255)
    private String storageIn;

    @Column(name = "conversion_ratio_to_mt")
    @NotNull
    @Min(value = 0)
    private Double conversionRatioToMt;

    @Column(name = "fumigable")
    @NotNull
    private Boolean fumigable;

    @Column(name = "needs_lot_number")
    @NotNull
    private Boolean needsLotNumber;

    @NotAudited
    @ManyToMany(mappedBy = "commodity", fetch = FetchType.LAZY)
    private Set<Client> client;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return name + " ( Unit of Measure: " + unitOfMeasure + " - Storage in: " + storageIn + " )";
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", storageIn='" + storageIn + '\'' +
                ", conversionRatioToMt=" + conversionRatioToMt +
                '}';
    }
}
