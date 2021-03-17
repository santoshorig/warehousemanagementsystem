package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Outward Truck Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardTruckForm {

    @NotNull
    private OutwardTruck outwardTruck;

    private MultipartFile gatePassDocument;

    private MultipartFile weighmentDocument;

    private MultipartFile qualityCheckDocument;
}
