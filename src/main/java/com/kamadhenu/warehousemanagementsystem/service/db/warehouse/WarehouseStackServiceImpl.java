package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseStackServiceImpl implements WarehouseStackService {

    @Autowired
    private WarehouseStackRepository warehouseStackRepository;

    /**
     * Get warehouse stack
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseStack> get(Integer id) {
        return warehouseStackRepository.findById(id);
    }

    /**
     * Edit warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    @Override
    public WarehouseStack edit(WarehouseStack warehouseStack) {
        return warehouseStackRepository.save(warehouseStack);
    }

    /**
     * Delete warehouse stack
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseStackRepository.deleteById(id);
    }

    /**
     * Get all warehouse stack basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseStack> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseStackRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse stack
     *
     * @return
     */
    @Override
    public List<WarehouseStack> getAll() {
        return warehouseStackRepository.findAll();
    }

    /**
     * Get warehouse stack count
     *
     * @return
     */
    public Long count() {
        return warehouseStackRepository.count();
    }

    /**
     * Get warehouse stack by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseStack> getByWarehouse(Warehouse warehouse) {
        return warehouseStackRepository.findByWarehouse(warehouse);
    }

    /**
     * Get non full warehouse stack by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseStack> getNonFullByWarehouse(Warehouse warehouse) {
        return warehouseStackRepository.findByWarehouseAndFull(warehouse, false);
    }
}
