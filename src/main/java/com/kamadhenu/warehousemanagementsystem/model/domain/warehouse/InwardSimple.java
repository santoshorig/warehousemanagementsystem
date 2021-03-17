package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Inward Simple Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InwardSimple {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward inward;

    @NotNull
    private Integer totalBags;

    @NotNull
    private Double totalNetWeight;

    /**
     * Get friendly name
     *
     * @return
     */
    ///TODO Get from messages.properties file
    public String getFriendlyName() {
        return inward.getFriendlyName() + " Total Units: " + totalBags + " Total Net Weight (Mt): " + totalNetWeight;
    }
}
