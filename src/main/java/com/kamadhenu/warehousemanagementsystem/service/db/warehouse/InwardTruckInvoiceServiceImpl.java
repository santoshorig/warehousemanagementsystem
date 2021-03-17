package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardTruckInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InwardTruckInvoiceServiceImpl implements InwardTruckInvoiceService {

    @Autowired
    private InwardTruckInvoiceRepository inwardTruckInvoiceRepository;

    /**
     * Get inward truck invoice
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardTruckInvoice> get(Integer id) {
        return inwardTruckInvoiceRepository.findById(id);
    }

    /**
     * Edit inward truck invoice
     *
     * @param inwardTruckInvoice
     * @return
     */
    @Override
    public InwardTruckInvoice edit(InwardTruckInvoice inwardTruckInvoice) {
        return inwardTruckInvoiceRepository.save(inwardTruckInvoice);
    }

    /**
     * Delete inward truck invoice
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardTruckInvoiceRepository.deleteById(id);
    }

    /**
     * Get all inward truck invoice basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardTruckInvoice> getAll(Integer pageNumber, Integer pageSize) {
        return inwardTruckInvoiceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward truck invoice
     *
     * @return
     */
    @Override
    public List<InwardTruckInvoice> getAll() {
        return inwardTruckInvoiceRepository.findAll();
    }

    /**
     * Get inward truck invoice count
     *
     * @return
     */
    public Long count() {
        return inwardTruckInvoiceRepository.count();
    }

    /**
     * Get inward truck invoice by inward truck
     *
     * @param inwardTruck
     * @return
     */
    public List<InwardTruckInvoice> getByInwardTruck(InwardTruck inwardTruck) {
        return inwardTruckInvoiceRepository.findByInwardTruck(inwardTruck);
    }
}
