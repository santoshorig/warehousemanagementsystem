package com.kamadhenu.warehousemanagementsystem.service.db.hr;

import com.kamadhenu.warehousemanagementsystem.model.db.hr.EmployeePosition;
import com.kamadhenu.warehousemanagementsystem.repository.hr.EmployeePositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeePositionServiceImpl implements EmployeePositionService {

    @Autowired
    private EmployeePositionRepository employeePositionRepository;

    /**
     * Get employee position
     *
     * @param id
     * @return
     */
    @Override
    public Optional<EmployeePosition> get(Integer id) {
        return employeePositionRepository.findById(id);
    }

    /**
     * Edit employee position
     *
     * @param employeePosition
     * @return
     */
    @Override
    public EmployeePosition edit(EmployeePosition employeePosition) {
        return employeePositionRepository.save(employeePosition);
    }

    /**
     * Delete employee position
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        employeePositionRepository.deleteById(id);
    }

    /**
     * Get all employee position basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<EmployeePosition> getAll(Integer pageNumber, Integer pageSize) {
        return employeePositionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all employee position
     *
     * @return
     */
    @Override
    public List<EmployeePosition> getAll() {
        return employeePositionRepository.findAll();
    }

    /**
     * Get employee position count
     *
     * @return
     */
    public Long count() {
        return employeePositionRepository.count();
    }
}
