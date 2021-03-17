package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Lr Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LrForm {

    @NotNull
    private Sr sr;

    @NotNull
    private Lr lr;

    @NotNull
    private Double totalWeight;

    @NotNull
    private List<LrInwardForm> lrInwardFormList;

    @NotNull
    private MultipartFile roUpload;

    @NotNull
    private MultipartFile roEmailUpload;

    @NotNull
    private String remark;

}
