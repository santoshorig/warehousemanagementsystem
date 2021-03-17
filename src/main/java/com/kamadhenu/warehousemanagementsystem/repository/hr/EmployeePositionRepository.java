package com.kamadhenu.warehousemanagementsystem.repository.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Employee Position repository class
 */
@Repository
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Integer> {

}
