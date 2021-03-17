package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseOwner;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseOwnerServiceImpl implements WarehouseOwnerService {

    @Autowired
    private WarehouseOwnerRepository warehouseOwnerRepository;

    /**
     * Get warehouse owner
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseOwner> get(Integer id) {
        return warehouseOwnerRepository.findById(id);
    }

    /**
     * Edit warehouse owner
     *
     * @param warehouseOwner
     * @return
     */
    @Override
    public WarehouseOwner edit(WarehouseOwner warehouseOwner) {
        return warehouseOwnerRepository.save(warehouseOwner);
    }

    /**
     * Delete warehouse owner
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseOwnerRepository.deleteById(id);
    }

    /**
     * Get all warehouse owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseOwner> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseOwnerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse owner
     *
     * @return
     */
    @Override
    public List<WarehouseOwner> getAll() {
        return warehouseOwnerRepository.findAll();
    }

    /**
     * Get warehouse owner count
     *
     * @return
     */
    public Long count() {
        return warehouseOwnerRepository.count();
    }

    /**
     * Get warehouse owner by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseOwner> getByWarehouse(Warehouse warehouse) {
        return warehouseOwnerRepository.findByWarehouse(warehouse);
    }
}
