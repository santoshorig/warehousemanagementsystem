package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.finance.SpotPrice;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ContractStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractRepository;
import com.kamadhenu.warehousemanagementsystem.service.db.finance.SpotPriceService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private SpotPriceService spotPriceService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Get contract
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Contract> get(Integer id) {
        return contractRepository.findById(id);
    }

    /**
     * Edit contract
     *
     * @param contract
     * @return
     */
    @Override
    public Contract edit(Contract contract) {
        return contractRepository.save(contract);
    }

    /**
     * Delete contract
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractRepository.deleteById(id);
    }

    /**
     * Get all contract basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Contract> getAll(Integer pageNumber, Integer pageSize) {
        return contractRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract
     *
     * @return
     */
    @Override
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    /**
     * Get contract count
     *
     * @return
     */
    public Long count() {
        return contractRepository.count();
    }

    /**
     * Get contract by status
     *
     * @return
     */
    public List<Contract> getByStatusAndBusinessType() {
        return contractRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getContractStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get contracts by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Contract> getByStatusAndBusinessType(List<String> statusList) {
        return contractRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get contracts by parent contract
     *
     * @param parentContract
     * @return
     */
    public List<Contract> getByParentContract(Contract parentContract) {
        return contractRepository.findByParentContract(parentContract);
    }

    /**
     * Get contracts which are approved
     *
     * @return
     */
    public List<Contract> getApprovedContract() {
        List<String> statusList = new ArrayList<>();
        statusList.add(constantService.getCONTRACT_STATUS().get("approved"));
        return getByStatusAndBusinessType(statusList);
    }

    /**
     * Get contract count by status
     *
     * @return
     */
    public List<ContractStatusCount> getCountContractByStatus() {
        return contractRepository.countContractByStatus();
    }

    /**
     * Get spot price by contract
     *
     * @param contract
     * @return
     */
    public Double getSpotPriceByContract(Contract contract, Date srDate) {
        Double spotPrice = 0.0;
        Warehouse warehouse = contract.getWarehouse();
        Integer regionLocationId = warehouse.getRegLoc();
        Optional<RegionLocation> regionLocation = regionLocationService.get(regionLocationId);
        if (regionLocation.isPresent()) {
            Set<Commodity> commoditySet = contract.getCommodity();
            for (Commodity commodity : commoditySet) {
                Location location = regionLocation.get().getLocation();
                Date now = srDate;
                List<SpotPrice> spotPriceList = spotPriceService
                        .getByCommodityAndFromDateAndToDateAndLocation(commodity, now, now, location);
                for (SpotPrice existingSpotPrice : spotPriceList) {
                    spotPrice = existingSpotPrice.getPrice();
                    break;
                }
                break;
            }
        }
        return spotPrice;
    }
}