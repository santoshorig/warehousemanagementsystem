package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Outward Simple Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardSimple {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward outward;

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
        return outward.getFriendlyName() + " Total Units: " + totalBags + " Total Net Weight (Mt): " + totalNetWeight;
    }
}
