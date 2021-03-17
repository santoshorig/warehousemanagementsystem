package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseConsumable;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseConsumableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseConsumableServiceImpl implements WarehouseConsumableService {

    @Autowired
    private WarehouseConsumableRepository warehouseConsumableRepository;

    /**
     * Get warehouse consumable
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseConsumable> get(Integer id) {
        return warehouseConsumableRepository.findById(id);
    }

    /**
     * Edit warehouse consumable
     *
     * @param warehouseConsumable
     * @return
     */
    @Override
    public WarehouseConsumable edit(WarehouseConsumable warehouseConsumable) {
        return warehouseConsumableRepository.save(warehouseConsumable);
    }

    /**
     * Delete warehouse consumable
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseConsumableRepository.deleteById(id);
    }

    /**
     * Get all warehouse consumable basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseConsumable> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseConsumableRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse consumable
     *
     * @return
     */
    @Override
    public List<WarehouseConsumable> getAll() {
        return warehouseConsumableRepository.findAll();
    }

    /**
     * Get warehouse consumable count
     *
     * @return
     */
    public Long count() {
        return warehouseConsumableRepository.count();
    }

    /**
     * Get warehouse consumable by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseConsumable> getByWarehouse(Warehouse warehouse) {
        return warehouseConsumableRepository.findByWarehouse(warehouse);
    }
}
