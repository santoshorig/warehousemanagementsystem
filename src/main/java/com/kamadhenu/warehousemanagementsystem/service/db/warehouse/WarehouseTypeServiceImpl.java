package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseType;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseTypeServiceImpl implements WarehouseTypeService {

    @Autowired
    private WarehouseTypeRepository warehouseTypeRepository;

    /**
     * Get warehouse type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseType> get(Integer id) {
        return warehouseTypeRepository.findById(id);
    }

    /**
     * Edit warehouse type
     *
     * @param warehouseType
     * @return
     */
    @Override
    public WarehouseType edit(WarehouseType warehouseType) {
        return warehouseTypeRepository.save(warehouseType);
    }

    /**
     * Delete warehouse type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseTypeRepository.deleteById(id);
    }

    /**
     * Get all warehouse type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseType> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse type
     *
     * @return
     */
    @Override
    public List<WarehouseType> getAll() {
        return warehouseTypeRepository.findAll();
    }

    /**
     * Get warehouse type count
     *
     * @return
     */
    public Long count() {
        return warehouseTypeRepository.count();
    }
}
