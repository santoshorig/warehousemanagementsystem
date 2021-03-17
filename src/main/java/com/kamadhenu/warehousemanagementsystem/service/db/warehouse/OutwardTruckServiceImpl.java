package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardTruckServiceImpl implements OutwardTruckService {

    @Autowired
    private OutwardTruckRepository outwardTruckRepository;

    /**
     * Get outward truck
     *
     * @param id
     * @return
     */
    @Override
    public Optional<OutwardTruck> get(Integer id) {
        return outwardTruckRepository.findById(id);
    }

    /**
     * Edit outward truck
     *
     * @param outwardTruck
     * @return
     */
    @Override
    public OutwardTruck edit(OutwardTruck outwardTruck) {
        return outwardTruckRepository.save(outwardTruck);
    }

    /**
     * Delete outward truck
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardTruckRepository.deleteById(id);
    }

    /**
     * Get all outward truck basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<OutwardTruck> getAll(Integer pageNumber, Integer pageSize) {
        return outwardTruckRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward truck
     *
     * @return
     */
    @Override
    public List<OutwardTruck> getAll() {
        return outwardTruckRepository.findAll();
    }

    /**
     * Get outward truck count
     *
     * @return
     */
    public Long count() {
        return outwardTruckRepository.count();
    }

    /**
     * Get outward truck by outward
     *
     * @param outward
     * @return
     */
    public List<OutwardTruck> getByOutward(Outward outward) {
        return outwardTruckRepository.findByOutward(outward);
    }
}
