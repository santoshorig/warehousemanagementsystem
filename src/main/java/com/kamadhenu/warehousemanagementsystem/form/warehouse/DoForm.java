package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Do Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DoForm {

    @NotNull
    private Do doModel;

    @NotNull
    private MultipartFile doLetterUpload;

    @NotNull
    private MultipartFile doEmailUpload;

    @NotNull
    private MultipartFile doKycUpload;
}
