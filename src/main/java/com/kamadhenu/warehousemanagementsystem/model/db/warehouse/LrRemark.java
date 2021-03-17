package com.kamadhenu.warehousemanagementsystem.model.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Lr Remark model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "lr_remark")
public class LrRemark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "remark")
    @NotNull
    @Size(min = 2)
    private String remark;

    @Column(name = "remark_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date remarkDate;

    @Column(name = "status")
    @NotNull
    @Size(min = 2, max = 255)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "lr")
    private Lr lr;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;
}
