package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * SrInward Truck Weighted Quality Check Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SrInwardTruckWeightedQualityCheck {

    @NotNull
    private QualityParameter qualityParameter;

    @NotNull
    private Double value;
    
    @NotNull
    private Double srQcTestResult;

}
