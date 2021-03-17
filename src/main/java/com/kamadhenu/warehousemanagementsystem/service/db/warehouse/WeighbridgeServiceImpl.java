package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Weighbridge;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WeighbridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeighbridgeServiceImpl implements WeighbridgeService {

    @Autowired
    private WeighbridgeRepository weighbridgeRepository;

    /**
     * Get weighbridge
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Weighbridge> get(Integer id) {
        return weighbridgeRepository.findById(id);
    }

    /**
     * Edit weighbridge
     *
     * @param weighbridge
     * @return
     */
    @Override
    public Weighbridge edit(Weighbridge weighbridge) {
        return weighbridgeRepository.save(weighbridge);
    }

    /**
     * Delete weighbridge
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        weighbridgeRepository.deleteById(id);
    }

    /**
     * Get all weighbridge basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Weighbridge> getAll(Integer pageNumber, Integer pageSize) {
        return weighbridgeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all weighbridge
     *
     * @return
     */
    @Override
    public List<Weighbridge> getAll() {
        return weighbridgeRepository.findAll();
    }

    /**
     * Get weighbridge count
     *
     * @return
     */
    public Long count() {
        return weighbridgeRepository.count();
    }

    /**
     * Get weighbridge by location
     *
     * @param location
     * @return
     */
    public List<Weighbridge> getByLocation(Location location) {
        return weighbridgeRepository.findByLocation(location);
    }
}
