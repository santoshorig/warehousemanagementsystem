package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward Truck Invoice repository class
 */
@Repository
public interface InwardTruckInvoiceRepository extends JpaRepository<InwardTruckInvoice, Integer> {

    /**
     * Find inward invoice by inward truck
     *
     * @param inwardTruck
     * @return
     */
    List<InwardTruckInvoice> findByInwardTruck(InwardTruck inwardTruck);
}
