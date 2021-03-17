package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inspection;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionRepository inspectionRepository;

    /**
     * Get inspection
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Inspection> get(Integer id) {
        return inspectionRepository.findById(id);
    }

    /**
     * Edit inspection
     *
     * @param inspection
     * @return
     */
    @Override
    public Inspection edit(Inspection inspection) {
        return inspectionRepository.save(inspection);
    }

    /**
     * Delete inspection
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inspectionRepository.deleteById(id);
    }

    /**
     * Get all inspection basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Inspection> getAll(Integer pageNumber, Integer pageSize) {
        return inspectionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inspection
     *
     * @return
     */
    @Override
    public List<Inspection> getAll() {
        return inspectionRepository.findAll();
    }

    /**
     * Get inspection count
     *
     * @return
     */
    public Long count() {
        return inspectionRepository.count();
    }
}
