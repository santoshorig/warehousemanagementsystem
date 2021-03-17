package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Supplier;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * Get supplier
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Supplier> get(Integer id) {
        return supplierRepository.findById(id);
    }

    /**
     * Edit supplier
     *
     * @param supplier
     * @return
     */
    @Override
    public Supplier edit(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    /**
     * Delete supplier
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        supplierRepository.deleteById(id);
    }

    /**
     * Get all supplier basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Supplier> getAll(Integer pageNumber, Integer pageSize) {
        return supplierRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all supplier
     *
     * @return
     */
    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    /**
     * Get supplier count
     *
     * @return
     */
    public Long count() {
        return supplierRepository.count();
    }
}
