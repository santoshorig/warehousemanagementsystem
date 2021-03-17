package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDocument;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseDocumentServiceImpl implements WarehouseDocumentService {

    @Autowired
    private WarehouseDocumentRepository warehouseDocumentRepository;

    /**
     * Get warehouse document
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WarehouseDocument> get(Integer id) {
        return warehouseDocumentRepository.findById(id);
    }

    /**
     * Edit warehouse document
     *
     * @param warehouseDocument
     * @return
     */
    @Override
    public WarehouseDocument edit(WarehouseDocument warehouseDocument) {
        return warehouseDocumentRepository.save(warehouseDocument);
    }

    /**
     * Delete warehouse document
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseDocumentRepository.deleteById(id);
    }

    /**
     * Get all warehouse document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WarehouseDocument> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseDocumentRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse document
     *
     * @return
     */
    @Override
    public List<WarehouseDocument> getAll() {
        return warehouseDocumentRepository.findAll();
    }

    /**
     * Get warehouse document count
     *
     * @return
     */
    public Long count() {
        return warehouseDocumentRepository.count();
    }

    /**
     * Get warehouse document by warehouse
     *
     * @param warehouse
     * @return
     */
    public List<WarehouseDocument> getByWarehouse(Warehouse warehouse) {
        return warehouseDocumentRepository.findByWarehouse(warehouse);
    }
}
