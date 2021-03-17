package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardTruckQualityCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardTruckQualityCheckServiceImpl implements OutwardTruckQualityCheckService {

    @Autowired
    private OutwardTruckQualityCheckRepository outwardTruckQualityCheckRepository;

    /**
     * Get outward truck quality check
     *
     * @param id
     * @return
     */
    @Override
    public Optional<OutwardTruckQualityCheck> get(Integer id) {
        return outwardTruckQualityCheckRepository.findById(id);
    }

    /**
     * Edit outward truck quality check
     *
     * @param outwardTruckQualityCheck
     * @return
     */
    @Override
    public OutwardTruckQualityCheck edit(OutwardTruckQualityCheck outwardTruckQualityCheck) {
        return outwardTruckQualityCheckRepository.save(outwardTruckQualityCheck);
    }

    /**
     * Delete outward truck quality check
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardTruckQualityCheckRepository.deleteById(id);
    }

    /**
     * Get all outward truck quality check basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<OutwardTruckQualityCheck> getAll(Integer pageNumber, Integer pageSize) {
        return outwardTruckQualityCheckRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward truck quality check
     *
     * @return
     */
    @Override
    public List<OutwardTruckQualityCheck> getAll() {
        return outwardTruckQualityCheckRepository.findAll();
    }

    /**
     * Get outward truck quality check count
     *
     * @return
     */
    public Long count() {
        return outwardTruckQualityCheckRepository.count();
    }

    /**
     * Get outward truck quality check by outward truck
     *
     * @param outwardTruck
     * @return
     */
    public List<OutwardTruckQualityCheck> getByOutwardTruck(OutwardTruck outwardTruck) {
        return outwardTruckQualityCheckRepository.findByOutwardTruck(outwardTruck);
    }
}
