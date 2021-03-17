package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Inward Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardForm {

    @NotNull
    private Inward inward;

    @NotNull
    private MultipartFile cadDocument;

    @NotNull
    private MultipartFile cddDocument;

    private MultipartFile grnDocument;
}
