package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import com.kamadhenu.warehousemanagementsystem.repository.general.UtilizationFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizationFactorServiceImpl implements UtilizationFactorService {

    @Autowired
    private UtilizationFactorRepository utilizationFactorRepository;

    /**
     * Get utilization factor
     *
     * @param id
     * @return
     */
    @Override
    public Optional<UtilizationFactor> get(Integer id) {
        return utilizationFactorRepository.findById(id);
    }

    /**
     * Edit utilization factor
     *
     * @param utilizationFactor
     * @return
     */
    @Override
    public UtilizationFactor edit(UtilizationFactor utilizationFactor) {
        return utilizationFactorRepository.save(utilizationFactor);
    }

    /**
     * Delete utilization factor
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        utilizationFactorRepository.deleteById(id);
    }

    /**
     * Get all utilization factor basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<UtilizationFactor> getAll(Integer pageNumber, Integer pageSize) {
        return utilizationFactorRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all utilization factor
     *
     * @return
     */
    @Override
    public List<UtilizationFactor> getAll() {
        return utilizationFactorRepository.findAll();
    }

    /**
     * Get utilization factor count
     *
     * @return
     */
    public Long count() {
        return utilizationFactorRepository.count();
    }

    /**
     * Get by commodity and business type
     *
     * @param commodity
     * @param businessType
     * @return
     */
    public List<UtilizationFactor> getByCommodityAndBusinessType(Commodity commodity, BusinessType businessType) {
        return utilizationFactorRepository.findByCommodityAndBusinessType(commodity, businessType);
    }
}
