package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseCommodityServiceImpl implements WarehouseCommodityService {

    @Autowired
    private WarehouseCommodityRepository warehouseCommodityRepository;

    /**
     * Get warehouse commodity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseCommodity> get(Integer id) {
        return warehouseCommodityRepository.findById(id);
    }

    /**
     * Edit warehouse commodity
     *
     * @param warehouseCommodity
     * @return
     */
    @Override
    public WarehouseCommodity edit(WarehouseCommodity warehouseCommodity) {
        return warehouseCommodityRepository.save(warehouseCommodity);
    }

    /**
     * Delete warehouse commodity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseCommodityRepository.deleteById(id);
    }

    /**
     * Get all warehouse commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseCommodity> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseCommodityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse commodity
     *
     * @return
     */
    @Override
    public List<WarehouseCommodity> getAll() {
        return warehouseCommodityRepository.findAll();
    }

    /**
     * Get warehouse commodity count
     *
     * @return
     */
    public Long count() {
        return warehouseCommodityRepository.count();
    }

    /**
     * Get warehouse commodity by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseCommodity> getByWarehouse(Warehouse warehouse) {
        return warehouseCommodityRepository.findByWarehouse(warehouse);
    }
}
