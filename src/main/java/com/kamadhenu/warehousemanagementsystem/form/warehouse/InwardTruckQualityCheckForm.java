package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Inward Truck Quality Check Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardTruckQualityCheckForm {

    @NotNull
    private List<InwardTruckQualityCheck> inwardTruckQualityCheckList;

    @NotNull
    private InwardTruck inwardTruck;

    @NotNull
    private MultipartFile qualityCheckDocument;
}
