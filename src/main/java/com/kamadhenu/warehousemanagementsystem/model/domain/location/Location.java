package com.kamadhenu.warehousemanagementsystem.model.domain.location;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Location Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Location {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.location.Location location;

    @NotNull
    private Integer warehouseCount;

    @NotNull
    private Integer clientCount;

    /**
     * Check if location has data
     *
     * @return
     */
    public Boolean hasData() {
        return warehouseCount > 0 || clientCount > 0;
    }

}
