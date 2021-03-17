package com.kamadhenu.warehousemanagementsystem.form.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Client Document Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientDocumentForm {

    @NotNull
    private ClientDocument clientDocument;

    @NotNull
    private MultipartFile upload;
}
