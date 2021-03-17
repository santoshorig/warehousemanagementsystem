package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseAsset;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseAssetServiceImpl implements WarehouseAssetService {

    @Autowired
    private WarehouseAssetRepository warehouseAssetRepository;

    /**
     * Get warehouse asset
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseAsset> get(Integer id) {
        return warehouseAssetRepository.findById(id);
    }

    /**
     * Edit warehouse asset
     *
     * @param warehouseAsset
     * @return
     */
    @Override
    public WarehouseAsset edit(WarehouseAsset warehouseAsset) {
        return warehouseAssetRepository.save(warehouseAsset);
    }

    /**
     * Delete warehouse asset
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseAssetRepository.deleteById(id);
    }

    /**
     * Get all warehouse asset basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseAsset> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseAssetRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse asset
     *
     * @return
     */
    @Override
    public List<WarehouseAsset> getAll() {
        return warehouseAssetRepository.findAll();
    }

    /**
     * Get warehouse asset count
     *
     * @return
     */
    public Long count() {
        return warehouseAssetRepository.count();
    }

    /**
     * Get warehouse asset by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseAsset> getByWarehouse(Warehouse warehouse) {
        return warehouseAssetRepository.findByWarehouse(warehouse);
    }
}
