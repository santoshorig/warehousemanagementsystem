package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Inspection Option model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "inspection_option")
public class InspectionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "label")
    @NotNull
    @Size(min = 1, max = 255)
    private String label;

    @Column(name = "value")
    @NotNull
    @Size(min = 2)
    private String value;

    @Column(name = "score")
    @NotNull
    @Min(value = 0)
    private Double score;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inspection")
    private Inspection inspection;

    /**
     * Get friendly name
     *
     * @return
     */
    public String getFriendlyName() {
        return "(" + label + ") " + value;
    }
}
