package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.ReportingDateWeightCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InwardTruckServiceImpl implements InwardTruckService {

    @Autowired
    private InwardTruckRepository inwardTruckRepository;

    /**
     * Get inward truck
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardTruck> get(Integer id) {
        return inwardTruckRepository.findById(id);
    }

    /**
     * Edit inward truck
     *
     * @param inwardTruck
     * @return
     */
    @Override
    public InwardTruck edit(InwardTruck inwardTruck) {
        return inwardTruckRepository.save(inwardTruck);
    }

    /**
     * Delete inward truck
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardTruckRepository.deleteById(id);
    }

    /**
     * Get all inward truck basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardTruck> getAll(Integer pageNumber, Integer pageSize) {
        return inwardTruckRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward truck
     *
     * @return
     */
    @Override
    public List<InwardTruck> getAll() {
        return inwardTruckRepository.findAll();
    }

    /**
     * Get inward truck count
     *
     * @return
     */
    public Long count() {
        return inwardTruckRepository.count();
    }

    /**
     * Get inward truck by inward
     *
     * @param inward
     * @return
     */
    public List<InwardTruck> getByInward(Inward inward) {
        return inwardTruckRepository.findByInward(inward);
    }

    /**
     * Get inward truck weight by reporting date
     *
     * @param statusList
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<ReportingDateWeightCount> getSumWeightByDateAndStatus(
            List<String> statusList,
            Date fromDate,
            Date toDate
    ) {
        return inwardTruckRepository.sumWeightByDateAndStatus(statusList, fromDate, toDate);
    }
}
