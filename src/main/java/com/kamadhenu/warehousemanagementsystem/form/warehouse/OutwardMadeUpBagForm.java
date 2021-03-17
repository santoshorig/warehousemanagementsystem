package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Outward Made Up Bag Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OutwardMadeUpBagForm {

    @NotNull
    private Outward outward;

    @NotNull
    private List<InwardMadeUpBag> inwardMadeUpBagList;
}
