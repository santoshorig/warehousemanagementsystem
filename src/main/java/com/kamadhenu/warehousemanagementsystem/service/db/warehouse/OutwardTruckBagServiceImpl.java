package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardTruckBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardTruckBagServiceImpl implements OutwardTruckBagService {

    @Autowired
    private OutwardTruckBagRepository outwardTruckBagRepository;

    /**
     * Get outward truck bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<OutwardTruckBag> get(Integer id) {
        return outwardTruckBagRepository.findById(id);
    }

    /**
     * Edit outward truck bag
     *
     * @param outwardTruckBag
     * @return
     */
    @Override
    public OutwardTruckBag edit(OutwardTruckBag outwardTruckBag) {
        return outwardTruckBagRepository.save(outwardTruckBag);
    }

    /**
     * Delete outward truck bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardTruckBagRepository.deleteById(id);
    }

    /**
     * Get all outward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<OutwardTruckBag> getAll(Integer pageNumber, Integer pageSize) {
        return outwardTruckBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward truck bag
     *
     * @return
     */
    @Override
    public List<OutwardTruckBag> getAll() {
        return outwardTruckBagRepository.findAll();
    }

    /**
     * Get outward truck bag count
     *
     * @return
     */
    public Long count() {
        return outwardTruckBagRepository.count();
    }

    /**
     * Get outward truck bag by outward truck
     *
     * @param outwardTruck
     * @return
     */
    public List<OutwardTruckBag> getByOutwardTruck(OutwardTruck outwardTruck) {
        return outwardTruckBagRepository.findByOutwardTruck(outwardTruck);
    }
}
