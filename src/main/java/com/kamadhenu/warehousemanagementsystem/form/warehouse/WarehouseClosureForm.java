package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Warehouse Closure Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseClosureForm {

    @NotNull
    private Boolean review;

    @NotNull
    private WarehouseClosure warehouseClosure;

    @NotNull
    private String remark;

    @NotNull
    private MultipartFile dehireLetter;
}
