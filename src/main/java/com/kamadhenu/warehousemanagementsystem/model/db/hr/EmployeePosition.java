package com.kamadhenu.warehousemanagementsystem.model.db.hr;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Employee Position model
 */
@Entity
@Audited


@Data
@ToString
@EqualsAndHashCode
@Table(name = "employee_position")
public class EmployeePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 255)
    private String name;
}
