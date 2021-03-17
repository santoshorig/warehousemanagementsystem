package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InspectionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectionTypeServiceImpl implements InspectionTypeService {

    @Autowired
    private InspectionTypeRepository inspectionTypeRepository;

    /**
     * Get inspection type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InspectionType> get(Integer id) {
        return inspectionTypeRepository.findById(id);
    }

    /**
     * Edit inspection type
     *
     * @param inspectionType
     * @return
     */
    @Override
    public InspectionType edit(InspectionType inspectionType) {
        return inspectionTypeRepository.save(inspectionType);
    }

    /**
     * Delete inspection type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inspectionTypeRepository.deleteById(id);
    }

    /**
     * Get all inspection type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InspectionType> getAll(Integer pageNumber, Integer pageSize) {
        return inspectionTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inspection type
     *
     * @return
     */
    @Override
    public List<InspectionType> getAll() {
        return inspectionTypeRepository.findAll();
    }

    /**
     * Get inspection type count
     *
     * @return
     */
    public Long count() {
        return inspectionTypeRepository.count();
    }
}
