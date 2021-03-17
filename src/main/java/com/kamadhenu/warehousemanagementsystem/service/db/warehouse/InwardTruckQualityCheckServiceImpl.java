package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardTruckQualityCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InwardTruckQualityCheckServiceImpl implements InwardTruckQualityCheckService {

    @Autowired
    private InwardTruckQualityCheckRepository inwardTruckQualityCheckRepository;

    /**
     * Get inward truck quality check
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardTruckQualityCheck> get(Integer id) {
        return inwardTruckQualityCheckRepository.findById(id);
    }

    /**
     * Edit inward truck quality check
     *
     * @param inwardTruckQualityCheck
     * @return
     */
    @Override
    public InwardTruckQualityCheck edit(InwardTruckQualityCheck inwardTruckQualityCheck) {
        return inwardTruckQualityCheckRepository.save(inwardTruckQualityCheck);
    }

    /**
     * Delete inward truck quality check
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardTruckQualityCheckRepository.deleteById(id);
    }

    /**
     * Get all inward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize) {
        return inwardTruckQualityCheckRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward truck quality check
     *
     * @return
     */
    @Override
    public List<InwardTruckQualityCheck> getAll() {
        return inwardTruckQualityCheckRepository.findAll();
    }

    /**
     * Get inward truck quality check count
     *
     * @return
     */
    public Long count() {
        return inwardTruckQualityCheckRepository.count();
    }

    /**
     * Get inward truck quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    public List<InwardTruckQualityCheck> getByInwardTruck(InwardTruck inwardTruck) {
        return inwardTruckQualityCheckRepository.findByInwardTruck(inwardTruck);
    }
}
