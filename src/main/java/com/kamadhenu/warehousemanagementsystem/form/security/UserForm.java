package com.kamadhenu.warehousemanagementsystem.form.security;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * User Form
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserForm {

    @NotNull
    private User user;

    @NotNull
    private List<Location> locationList;
}
