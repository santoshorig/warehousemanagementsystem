package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;

/**
 * Commodity Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Commodity {

    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity commodity;

    @NotNull
    private WarehouseCommodity warehouseCommodity;

    @NotNull
    private UtilizationFactor utilizationFactor;

    @NotNull
    private Warehouse warehouse;

    @NotNull
    private WarehouseDetail warehouseDetail;

    /**
     * Get capacity utilization in MT
     *
     * @return
     */
    public Double getCapacityUtilization() {
        Double capacityUtilization = 0.0;
        if (utilizationFactor != null) {
            capacityUtilization = ((warehouseDetail.getArea() * warehouseCommodity
                    .getPlannedUtilization() * commodity.getConversionRatioToMt()) / (100 * utilizationFactor
                    .getFactor()));
        }
        return capacityUtilization;
    }

    /**
     * Get expected AUM
     *
     * @return
     */
    public Double getExpectedAUM() {
        return getCapacityUtilization() * warehouseCommodity.getPricePerMt();
    }

    /**
     * Get funding value
     *
     * @return
     */
    public Double getFundingValue() {
        return (getExpectedAUM() * warehouseCommodity.getFundingPercentage() / 100);
    }

    /**
     * Get spot price
     *
     * @return
     */
    public Double getSpotPrice() {
        return warehouseCommodity.getPricePerMt();
    }

    /**
     * Get quality check details
     *
     * @return
     */
    public String getQualityCheckDetails() {
        return warehouseCommodity.getQualityCheckBy() + " ( " + warehouseCommodity.getQualityLab()
                .getFriendlyName() + " )";
    }

    /**
     * Get capacity via risk formula
     *
     * @return
     */
    public String getRiskCapacity() {
        return decimalFormat.format(warehouseDetail.getArea() / (100 * utilizationFactor.getFactor()));
    }
}
