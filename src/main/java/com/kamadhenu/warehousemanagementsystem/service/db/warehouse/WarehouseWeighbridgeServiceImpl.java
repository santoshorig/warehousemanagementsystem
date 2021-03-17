package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseWeighbridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseWeighbridgeServiceImpl implements WarehouseWeighbridgeService {

    @Autowired
    private WarehouseWeighbridgeRepository warehouseWeighbridgeRepository;

    /**
     * Get warehouse weighbridge
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseWeighbridge> get(Integer id) {
        return warehouseWeighbridgeRepository.findById(id);
    }

    /**
     * Edit warehouse weighbridge
     *
     * @param warehouseWeighbridge
     * @return
     */
    @Override
    public WarehouseWeighbridge edit(WarehouseWeighbridge warehouseWeighbridge) {
        return warehouseWeighbridgeRepository.save(warehouseWeighbridge);
    }

    /**
     * Delete warehouse weighbridge
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseWeighbridgeRepository.deleteById(id);
    }

    /**
     * Get all warehouse weighbridge basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseWeighbridge> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseWeighbridgeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse weighbridge
     *
     * @return
     */
    @Override
    public List<WarehouseWeighbridge> getAll() {
        return warehouseWeighbridgeRepository.findAll();
    }

    /**
     * Get warehouse weighbridge count
     *
     * @return
     */
    public Long count() {
        return warehouseWeighbridgeRepository.count();
    }

    /**
     * Get warehouse weighbridge by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseWeighbridge> getByWarehouse(Warehouse warehouse) {
        return warehouseWeighbridgeRepository.findByWarehouse(warehouse);
    }
}
