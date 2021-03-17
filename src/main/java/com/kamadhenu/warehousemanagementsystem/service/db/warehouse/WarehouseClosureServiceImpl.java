package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseClosureRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseClosureServiceImpl implements WarehouseClosureService {

    @Autowired
    private WarehouseClosureRepository warehouseClosureRepository;

    @Autowired
    private HelperService helperService;

    /**
     * Get warehouse closure
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseClosure> get(Integer id) {
        return warehouseClosureRepository.findById(id);
    }

    /**
     * Edit warehouse closure
     *
     * @param warehouseClosure
     * @return
     */
    @Override
    public WarehouseClosure edit(WarehouseClosure warehouseClosure) {
        return warehouseClosureRepository.save(warehouseClosure);
    }

    /**
     * Delete warehouse closure
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseClosureRepository.deleteById(id);
    }

    /**
     * Get all warehouse closure basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseClosure> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseClosureRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse closure
     *
     * @return
     */
    @Override
    public List<WarehouseClosure> getAll() {
        return warehouseClosureRepository.findAll();
    }

    /**
     * Get warehouse closure count
     *
     * @return
     */
    public Long count() {
        return warehouseClosureRepository.count();
    }

    /**
     * Get warehouse closure by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseClosure> getByWarehouse(Warehouse warehouse) {
        return warehouseClosureRepository.findByWarehouse(warehouse);
    }

    /**
     * Get warehouse closure by status
     *
     * @return
     */
    public List<WarehouseClosure> getByStatus() {
        return warehouseClosureRepository.findByStatusIn(helperService.getWarehouseClosureStatusByRole());
    }
}
