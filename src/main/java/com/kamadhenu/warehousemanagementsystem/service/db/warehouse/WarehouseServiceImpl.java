package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WarehouseRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private HelperService helperService;

    @Autowired
    private ConstantService constantService;

    /**
     * Get warehouse
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Warehouse> get(Integer id) {
        return warehouseRepository.findById(id);
    }

    /**
     * Edit warehouse
     *
     * @param warehouse
     * @return
     */
    @Override
    public Warehouse edit(Warehouse warehouse) {
        if (warehouse.getStatus()
                .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("pendingforriskassessment"))) {
            if (warehouse.getFirstSentToRiskAssessment() == null) {
                warehouse.setFirstSentToRiskAssessment(new Date());
            }
        } else if (warehouse.getStatus()
                .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("pendingforriskapproval"))) {
            if (warehouse.getFirstSentToRiskApprover() == null) {
                warehouse.setFirstSentToRiskApprover(new Date());
            }
        } else if (warehouse.getStatus()
                .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessapproval"))) {
            if (warehouse.getFirstSentToManagement() == null) {
                warehouse.setFirstSentToManagement(new Date());
                warehouse.setLastSentToManagement(new Date());
            } else {
                warehouse.setLastSentToManagement(new Date());
            }
        } else if (warehouse.getStatus().equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("rework"))) {
            warehouse.setLastRework(new Date());
        }

        return warehouseRepository.save(warehouse);
    }

    /**
     * Delete warehouse
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        warehouseRepository.deleteById(id);
    }

    /**
     * Get all warehouse basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Warehouse> getAll(Integer pageNumber, Integer pageSize) {
        return warehouseRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all warehouse
     *
     * @return
     */
    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    /**
     * Get warehouse count
     *
     * @return
     */
    public Long count() {
        return warehouseRepository.count();
    }

    /**
     * Get warehouse by status
     *
     * @return
     */
    public List<Warehouse> getByStatus() {
        return warehouseRepository
                .findByStatusIn(
                        helperService.getWarehouseStatusByRole()
                );
    }

    /**
     * Get warehouse by status
     *
     * @param statusList
     * @return
     */
    public List<Warehouse> getByStatus(List<String> statusList) {
        return warehouseRepository.findByStatusIn(statusList);
    }

    /**
     * Get warehouse by status and business type
     *
     * @return
     */
    public List<Warehouse> getByStatusAndBusinessType() {
        return warehouseRepository.findByStatusInAndBusinessTypeIn(
                helperService.getWarehouseStatusByRole(),
                helperService.getUserBusinessType()
        );
    }

    /**
     * Get active warehouse by business type
     *
     * @return
     */
    public List<Warehouse> getActiveByBusinessType() {
        return warehouseRepository.findByStatusInAndBusinessTypeIn(
                helperService.getApprovedWarehouseStatus(),
                helperService.getUserBusinessType()
        );
    }

    /**
     * Get warehouse count by location
     *
     * @return
     */
    public List<WarehouseLocationCount> getCountWarehouseByLocation() {
        return warehouseRepository.countWarehouseByLocation();
    }

    /**
     * Get warehouse count by status
     *
     * @return
     */
    public List<WarehouseStatusCount> getCountWarehouseByStatus() {
        return warehouseRepository.countWarehouseByStatus();
    }
}
