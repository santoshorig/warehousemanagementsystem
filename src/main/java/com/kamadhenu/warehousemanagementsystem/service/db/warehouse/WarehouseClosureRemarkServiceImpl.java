package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosureRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseClosureRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseClosureRemarkServiceImpl implements WarehouseClosureRemarkService {

    @Autowired
    private WarehouseClosureRemarkRepository warehouseClosureRemarkRepository;

    /**
     * Get warehouse closure remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseClosureRemark> get(Integer id) {
        return warehouseClosureRemarkRepository.findById(id);
    }

    /**
     * Edit warehouse closure remark
     *
     * @param warehouseClosureRemark
     * @return
     */
    @Override
    public WarehouseClosureRemark edit(WarehouseClosureRemark warehouseClosureRemark) {
        return warehouseClosureRemarkRepository.save(warehouseClosureRemark);
    }

    /**
     * Delete warehouse closure remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseClosureRemarkRepository.deleteById(id);
    }

    /**
     * Get all warehouse closure remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseClosureRemark> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseClosureRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse closure remark
     *
     * @return
     */
    @Override
    public List<WarehouseClosureRemark> getAll() {
        return warehouseClosureRemarkRepository.findAll();
    }

    /**
     * Get warehouse closure remark count
     *
     * @return
     */
    public Long count() {
        return warehouseClosureRemarkRepository.count();
    }

    /**
     * Get warehouse closure remark by warehouse closure
     *
     * @param warehouseClosure
     * @return
     */
    public List<WarehouseClosureRemark> getByWarehouseClosure(WarehouseClosure warehouseClosure) {
        return warehouseClosureRemarkRepository.findByWarehouseClosure(warehouseClosure);
    }
}
