package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * SrInward Truck Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SrInwardTruckQualityCheck {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck
            srInwardTruckQualityCheck;


    /**
     * Get dumping date
     *
     * @return
     */
    public Date getDumpingDate() {
        return srInwardTruckQualityCheck
                .getInwardTruck().getDumpingDate();
    }

    /**
     * Get total bags/bales/drums
     *
     * @return
     */
    public Integer getTotalBagsBalesDrums() {
        return srInwardTruckQualityCheck.getInwardTruck().getTotalBagsBalesDrums();
    }

    /**
     * Get total net weight
     *
     * @return
     */
    public Double getTotalNetWeight() {
        return (srInwardTruckQualityCheck.getInwardTruck().getGrossWeight() - srInwardTruckQualityCheck.getInwardTruck()
                .getTareWeight());
    }

    /**
     * Get cad
     *
     * @return
     */
    public String getCad() {
        return srInwardTruckQualityCheck.getInwardTruck().getInward().getSimpleCadName();
    }

    /**
     * Get warehouse
     *
     * @return
     */
    public String getWeighbridge() {
        return srInwardTruckQualityCheck.getInwardTruck().getWeighbridge() != null ? srInwardTruckQualityCheck
                .getInwardTruck().getWeighbridge().getFriendlyName() : null;
    }

    /**
     * Get quality check document
     *
     * @return
     */
    public Document getQualityCheckDocument() {
        return srInwardTruckQualityCheck
                .getInwardTruck().getQualityCheckDocument();
    }

    /**
     * Get vehicle number
     *
     * @return
     */
    public String getVehicleNumber() {
        return srInwardTruckQualityCheck.getInwardTruck().getVehicleNumber();
    }
}
