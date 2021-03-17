package com.kamadhenu.warehousemanagementsystem.model.domain.document;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Document Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Document {

    @NotNull
    private String documentFor;

    @NotNull
    private String documentType;

    @NotNull
    private Integer documentId;
}
