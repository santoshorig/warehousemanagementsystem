package com.kamadhenu.warehousemanagementsystem.repository.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Employee repository class
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Find by active and location and business type
     *
     * @param active
     * @param location
     * @param businessType
     * @return
     */
    List<Employee> findByActiveAndLocationAndBusinessTypeIn(
            Boolean active,
            Location location,
            List<BusinessType> businessType
    );

}
