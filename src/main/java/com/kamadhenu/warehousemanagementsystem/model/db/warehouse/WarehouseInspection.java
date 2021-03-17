package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Warehouse Inspection model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "warehouse_inspection")
public class WarehouseInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "risk_score")
    @Min(value = 0)
    private Double riskScore;

    @Column(name = "data")
    private String data;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "document")
    private Document document;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "inspection_option")
    private InspectionOption inspectionOption;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    /**
     * Get weighted score
     *
     * @return
     */
    public Double getWeightedRiskScore() {
        Double riskScore = getRiskScore() != null ? getRiskScore() : 0d;
        return (riskScore * inspectionOption.getInspection().getWeight()) / 100;
    }
}
