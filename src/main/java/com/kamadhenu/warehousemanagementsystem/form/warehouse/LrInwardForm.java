package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Lr Inward Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LrInwardForm {

    @NotNull
    private Inward inward;

    @NotNull
    private Integer totalBags;

    @NotNull
    private Double totalNetWeight;

    @NotNull
    private List<LrWarehouseStackForm> lrWarehouseStackFormList;

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
