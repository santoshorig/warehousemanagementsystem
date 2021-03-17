package com.kamadhenu.warehousemanagementsystem.service.db.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;

import java.util.List;
import java.util.Optional;

/**
 * Employee interface
 */
public interface EmployeeService {

    /**
     * Get employee
     *
     * @param id
     * @return
     */
    Optional<Employee> get(Integer id);

    /**
     * Edit employee
     *
     * @param employee
     * @return
     */
    Employee edit(Employee employee);

    /**
     * Delete employee
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all employee basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Employee> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all employee
     *
     * @return
     */
    List<Employee> getAll();

    /**
     * Get employee count
     *
     * @return
     */
    Long count();

    /**
     * Get employee by active, location and business type
     *
     * @param location
     * @return
     */
    List<Employee> getActiveByLocationAndBusinessType(Location location);
}
