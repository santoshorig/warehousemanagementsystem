package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.ReportingDateWeightCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Inward Truck interface
 */
public interface InwardTruckService {

    /**
     * Get inward truck
     *
     * @param id
     * @return
     */
    Optional<InwardTruck> get(Integer id);

    /**
     * Edit inward truck
     *
     * @param inwardTruck
     * @return
     */
    InwardTruck edit(InwardTruck inwardTruck);

    /**
     * Delete inward truck
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward truck basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<InwardTruck> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward truck
     *
     * @return
     */
    List<InwardTruck> getAll();

    /**
     * Get inward truck count
     *
     * @return
     */
    Long count();

    /**
     * Get inward truck by inward
     *
     * @param inward
     * @return
     */
    List<InwardTruck> getByInward(Inward inward);

    /**
     * Get inward truck weight by reporting date
     *
     * @param statusList
     * @param fromDate
     * @param toDate
     * @return
     */
    List<ReportingDateWeightCount> getSumWeightByDateAndStatus(List<String> statusList, Date fromDate, Date toDate);
}
