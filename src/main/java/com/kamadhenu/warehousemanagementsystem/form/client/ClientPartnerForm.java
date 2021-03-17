package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Client Partner Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientPartnerForm {

    @NotNull
    private ClientPartner clientPartner;

    @NotNull
    private MultipartFile highmarkUpload;

    @NotNull
    private MultipartFile cibilUpload;

}
