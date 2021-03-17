package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseDetailServiceImpl implements WarehouseDetailService {

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    /**
     * Get warehouse detail
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseDetail> get(Integer id) {
        return warehouseDetailRepository.findById(id);
    }

    /**
     * Edit warehouse detail
     *
     * @param warehouseDetail
     * @return
     */
    @Override
    public WarehouseDetail edit(WarehouseDetail warehouseDetail) {
        return warehouseDetailRepository.save(warehouseDetail);
    }

    /**
     * Delete warehouse detail
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseDetailRepository.deleteById(id);
    }

    /**
     * Get all warehouse detail basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseDetail> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseDetailRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse detail
     *
     * @return
     */
    @Override
    public List<WarehouseDetail> getAll() {
        return warehouseDetailRepository.findAll();
    }

    /**
     * Get warehouse detail count
     *
     * @return
     */
    public Long count() {
        return warehouseDetailRepository.count();
    }

    /**
     * Get warehouse detail by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseDetail> getByWarehouse(Warehouse warehouse) {
        return warehouseDetailRepository.findByWarehouse(warehouse);
    }
}
