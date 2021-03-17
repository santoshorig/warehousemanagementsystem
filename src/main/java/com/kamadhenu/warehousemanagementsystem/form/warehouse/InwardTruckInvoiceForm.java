package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Inward Truck Invoice Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardTruckInvoiceForm {

    @NotNull
    private InwardTruckInvoice inwardTruckInvoice;

    private MultipartFile invoiceDocument;
}
