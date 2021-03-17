package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.SrInwardTruckWeightedQualityCheck;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Sr Remark Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SrRemarkForm {

    @NotNull
    private Sr sr;

    @NotNull
    private List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList;

    @NotNull
    private List<SrInwardTruckWeightedQualityCheck> srInwardTruckWeightedQualityCheckList;

    @NotNull
    private Boolean revalidate;

    @NotNull
    private String remark;

    @NotNull
    private Boolean review;
}
