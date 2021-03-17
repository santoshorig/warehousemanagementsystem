package com.kamadhenu.warehousemanagementsystem.form.report;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Client Report Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientReportForm {

    @NotNull
    private String status;

}
