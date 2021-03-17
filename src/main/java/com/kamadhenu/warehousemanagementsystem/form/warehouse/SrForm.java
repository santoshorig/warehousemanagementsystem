package com.kamadhenu.warehousemanagementsystem.form.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Sr Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SrForm {

    @NotNull
    private Sr sr;

    @NotNull
    private List<Inward> inwardList;
}
