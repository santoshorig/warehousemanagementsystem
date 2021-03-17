package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Inward Truck Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardTruckForm {

    @NotNull
    private InwardTruck inwardTruck;

    private MultipartFile gatePassDocument;

    private MultipartFile weighmentDocument;

    private MultipartFile mandiReceiptDocument;

    private MultipartFile qualityCheckDocument;
}
