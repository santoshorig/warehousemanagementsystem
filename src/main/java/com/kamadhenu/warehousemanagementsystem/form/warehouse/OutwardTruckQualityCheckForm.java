package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Outward Truck Quality Check Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardTruckQualityCheckForm {

    @NotNull
    private List<OutwardTruckQualityCheck> outwardTruckQualityCheckList;

    @NotNull
    private OutwardTruck outwardTruck;

    @NotNull
    private MultipartFile qualityCheckDocument;
}
