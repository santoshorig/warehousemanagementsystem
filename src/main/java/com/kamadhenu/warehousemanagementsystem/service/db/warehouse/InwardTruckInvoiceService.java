package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;

import java.util.List;
import java.util.Optional;

/**
 * Inward Truck Invoice interface
 */
public interface InwardTruckInvoiceService {

    /**
     * Get inward truck invoice
     *
     * @param id
     * @return
     */
    Optional<InwardTruckInvoice> get(Integer id);

    /**
     * Edit inward truck invoice
     *
     * @param inwardTruckInvoice
     * @return
     */
    InwardTruckInvoice edit(InwardTruckInvoice inwardTruckInvoice);

    /**
     * Delete inward truck invoice
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward truck invoice basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardTruckInvoice> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward truck invoice
     *
     * @return
     */
    List<InwardTruckInvoice> getAll();

    /**
     * Get inward truck invoice count
     *
     * @return
     */
    Long count();

    /**
     * Get inward truck invoice by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckInvoice> getByInwardTruck(InwardTruck inwardTruck);
}
