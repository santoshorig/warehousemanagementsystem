package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseInspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseInspectionServiceImpl implements WarehouseInspectionService {

    @Autowired
    private WarehouseInspectionRepository warehouseInspectionRepository;

    /**
     * Get warehouse inspection
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseInspection> get(Integer id) {
        return warehouseInspectionRepository.findById(id);
    }

    /**
     * Edit warehouse inspection
     *
     * @param warehouseInspection
     * @return
     */
    @Override
    public WarehouseInspection edit(WarehouseInspection warehouseInspection) {
        return warehouseInspectionRepository.save(warehouseInspection);
    }

    /**
     * Delete warehouse inspection
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseInspectionRepository.deleteById(id);
    }

    /**
     * Get all warehouse inspection basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseInspection> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseInspectionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse inspection
     *
     * @return
     */
    @Override
    public List<WarehouseInspection> getAll() {
        return warehouseInspectionRepository.findAll();
    }

    /**
     * Get warehouse inspection count
     *
     * @return
     */
    public Long count() {
        return warehouseInspectionRepository.count();
    }

    /**
     * Get warehouse inspection by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseInspection> getByWarehouse(Warehouse warehouse) {
        return warehouseInspectionRepository.findByWarehouse(warehouse);
    }
}
