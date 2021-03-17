package com.kamadhenu.warehousemanagementsystem.form.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Region Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegionForm {

    @NotNull
    private State state;

    @NotNull
    private List<District> district;

    @NotNull
    private Region region;
}
