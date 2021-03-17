package com.kamadhenu.warehousemanagementsystem.service.db.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.EmployeePosition;

import java.util.List;
import java.util.Optional;

/**
 * EmployeePosition interface
 */
public interface EmployeePositionService {

    /**
     * Get employee position
     *
     * @param id
     * @return
     */
    Optional<EmployeePosition> get(Integer id);

    /**
     * Edit employee position
     *
     * @param employeePosition
     * @return
     */
    EmployeePosition edit(EmployeePosition employeePosition);

    /**
     * Delete employee position
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all employee position basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<EmployeePosition> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all employee position
     *
     * @return
     */
    List<EmployeePosition> getAll();

    /**
     * Get employee position count
     *
     * @return
     */
    Long count();
}
