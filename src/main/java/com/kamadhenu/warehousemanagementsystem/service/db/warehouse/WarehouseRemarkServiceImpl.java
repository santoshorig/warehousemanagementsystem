package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseRemark;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseRemarkServiceImpl implements WarehouseRemarkService {

    @Autowired
    private WarehouseRemarkRepository warehouseRemarkRepository;

    /**
     * Get warehouse remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseRemark> get(Integer id) {
        return warehouseRemarkRepository.findById(id);
    }

    /**
     * Edit warehouse remark
     *
     * @param WarehouseRemark
     * @return
     */
    @Override
    public WarehouseRemark edit(WarehouseRemark WarehouseRemark) {
        return warehouseRemarkRepository.save(WarehouseRemark);
    }

    /**
     * Delete warehouse remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseRemarkRepository.deleteById(id);
    }

    /**
     * Get all warehouse remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseRemark> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse remark
     *
     * @return
     */
    @Override
    public List<WarehouseRemark> getAll() {
        return warehouseRemarkRepository.findAll();
    }

    /**
     * Get warehouse remark count
     *
     * @return
     */
    public Long count() {
        return warehouseRemarkRepository.count();
    }

    /**
     * Get warehouse remark
     *
     * @param warehouse
     * @return
     */
    @Override
    public List<WarehouseRemark> getByWarehouse(Warehouse warehouse) {
        return warehouseRemarkRepository.findByWarehouseOrderByIdDescRemarkDateDesc(warehouse);
    }
}
