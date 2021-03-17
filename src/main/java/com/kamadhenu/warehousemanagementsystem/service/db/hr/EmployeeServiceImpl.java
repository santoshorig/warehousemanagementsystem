package com.kamadhenu.warehousemanagementsystem.service.db.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.repository.hr.EmployeeRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private HelperService helperService;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get employee
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Employee> get(Integer id) {
        return employeeRepository.findById(id);
    }

    /**
     * Edit employee
     *
     * @param employee
     * @return
     */
    @Override
    public Employee edit(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Delete employee
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Get all employee basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Employee> getAll(Integer pageNumber, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all employee
     *
     * @return
     */
    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    /**
     * Get employee count
     *
     * @return
     */
    public Long count() {
        return employeeRepository.count();
    }

    /**
     * Get employee by active, location and business type
     *
     * @param location
     * @return
     */
    public List<Employee> getActiveByLocationAndBusinessType(Location location) {
        return employeeRepository
                .findByActiveAndLocationAndBusinessTypeIn(true, location, helperService.getUserBusinessType());
    }
}
