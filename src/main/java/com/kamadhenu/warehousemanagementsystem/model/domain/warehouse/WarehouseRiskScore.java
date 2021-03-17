package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Warehouse Risk Score Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseRiskScore {

    @NotNull
    private InspectionType inspectionType;

    @NotNull
    private Double weightedRiskScore;

    /**
     * Weighted Total Score
     *
     * @return
     */
    public Double getWeightedTotalScore() {
        return (inspectionType.getWeight() * weightedRiskScore) / 100;
    }
}
