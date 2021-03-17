package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCad;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseCadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseCadServiceImpl implements WarehouseCadService {

    @Autowired
    private WarehouseCadRepository warehouseCadRepository;

    /**
     * Get warehouse cad
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseCad> get(Integer id) {
        return warehouseCadRepository.findById(id);
    }

    /**
     * Edit warehouse cad
     *
     * @param warehouseCad
     * @return
     */
    @Override
    public WarehouseCad edit(WarehouseCad warehouseCad) {
        return warehouseCadRepository.save(warehouseCad);
    }

    /**
     * Delete warehouse cad
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseCadRepository.deleteById(id);
    }

    /**
     * Get all warehouse cad basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseCad> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseCadRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse cad
     *
     * @return
     */
    @Override
    public List<WarehouseCad> getAll() {
        return warehouseCadRepository.findAll();
    }

    /**
     * Get warehouse cad count
     *
     * @return
     */
    public Long count() {
        return warehouseCadRepository.count();
    }

    /**
     * Get warehouse cad by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseCad> getByWarehouse(Warehouse warehouse) {
        return warehouseCadRepository.findByWarehouse(warehouse);
    }

    /**
     * Get warehouse cad by warehouse and used
     *
     * @param warehouse
     * @param used
     * @return
     */
    public List<WarehouseCad> getByWarehouseAndUsed(Warehouse warehouse, Boolean used) {
        return warehouseCadRepository.findByWarehouseAndUsed(warehouse, used);
    }
}
