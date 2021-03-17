package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseManpower;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseManpowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseManpowerServiceImpl implements WarehouseManpowerService {

    @Autowired
    private WarehouseManpowerRepository warehouseManpowerRepository;

    /**
     * Get warehouse manpower
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseManpower> get(Integer id) {
        return warehouseManpowerRepository.findById(id);
    }

    /**
     * Edit warehouse manpower
     *
     * @param warehouseManpower
     * @return
     */
    @Override
    public WarehouseManpower edit(WarehouseManpower warehouseManpower) {
        return warehouseManpowerRepository.save(warehouseManpower);
    }

    /**
     * Delete warehouse manpower
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseManpowerRepository.deleteById(id);
    }

    /**
     * Get all warehouse manpower basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseManpower> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseManpowerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse manpower
     *
     * @return
     */
    @Override
    public List<WarehouseManpower> getAll() {
        return warehouseManpowerRepository.findAll();
    }

    /**
     * Get warehouse manpower count
     *
     * @return
     */
    public Long count() {
        return warehouseManpowerRepository.count();
    }

    /**
     * Get warehouse manpower by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseManpower> getByWarehouse(Warehouse warehouse) {
        return warehouseManpowerRepository.findByWarehouse(warehouse);
    }
}
