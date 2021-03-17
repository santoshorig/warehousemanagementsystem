package com.kamadhenu.warehousemanagementsystem.form.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Employee Document Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EmployeeDocumentForm {

    @NotNull
    private Employee employee;

    private MultipartFile upload;
}
