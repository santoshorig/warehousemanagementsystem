package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.SrInwardTruckQualityCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrInwardTruckQualityCheckServiceImpl implements SrInwardTruckQualityCheckService {

    @Autowired
    private SrInwardTruckQualityCheckRepository srInwardTruckQualityCheckRepository;

    /**
     * Get sr inward truck quality check
     *
     * @param id
     * @return
     */
    @Override
    public Optional<SrInwardTruckQualityCheck> get(Integer id) {
        return srInwardTruckQualityCheckRepository.findById(id);
    }

    /**
     * Edit sr inward truck quality check
     *
     * @param srInwardTruckQualityCheck
     * @return
     */
    @Override
    public SrInwardTruckQualityCheck edit(SrInwardTruckQualityCheck srInwardTruckQualityCheck) {
        return srInwardTruckQualityCheckRepository.save(srInwardTruckQualityCheck);
    }

    /**
     * Delete sr inward truck quality check
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        srInwardTruckQualityCheckRepository.deleteById(id);
    }

    /**
     * Get all sr inward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<SrInwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize) {
        return srInwardTruckQualityCheckRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all sr inward truck quality check
     *
     * @return
     */
    @Override
    public List<SrInwardTruckQualityCheck> getAll() {
        return srInwardTruckQualityCheckRepository.findAll();
    }

    /**
     * Get sr inward truck quality check count
     *
     * @return
     */
    public Long count() {
        return srInwardTruckQualityCheckRepository.count();
    }

    /**
     * Get sr inward truck quality check by sr
     *
     * @param sr
     * @return
     */
    public List<SrInwardTruckQualityCheck> getBySr(Sr sr) {
        return srInwardTruckQualityCheckRepository.findBySr(sr);
    }

    /**
     * Get sr inward truck quality check by inward truck
     *
     * @param inwardTruck
     * @return
     */
    public List<SrInwardTruckQualityCheck> getByInwardTruck(InwardTruck inwardTruck) {
        return srInwardTruckQualityCheckRepository.findByInwardTruck(inwardTruck);
    }
}
