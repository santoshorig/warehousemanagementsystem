package com.kamadhenu.warehousemanagementsystem.model.domain.common;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Remark Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Remark {

    @NotNull
    private String remarkFor;

    @NotNull
    private String remark;

    @NotNull
    private Date remarkDate;

    @NotNull
    private User remarkUser;
}
