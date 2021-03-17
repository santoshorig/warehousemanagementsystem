package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Client Signatory Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientSignatoryForm {

    @NotNull
    private ClientSignatory clientSignatory;

    private MultipartFile upload;
}
