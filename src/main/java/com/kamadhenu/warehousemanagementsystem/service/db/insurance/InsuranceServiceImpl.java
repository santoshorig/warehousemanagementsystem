package com.kamadhenu.warehousemanagementsystem.service.db.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.repository.insurance.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    /**
     * Get insurance
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Insurance> get(Integer id) {
        return insuranceRepository.findById(id);
    }

    /**
     * Edit insurance
     *
     * @param insurance
     * @return
     */
    @Override
    public Insurance edit(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    /**
     * Delete insurance
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        insuranceRepository.deleteById(id);
    }

    /**
     * Get all insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Insurance> getAll(Integer pageNumber, Integer pageSize) {
        return insuranceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all insurance
     *
     * @return
     */
    @Override
    public List<Insurance> getAll() {
        return insuranceRepository.findAll();
    }

    /**
     * Get insurance count
     *
     * @return
     */
    public Long count() {
        return insuranceRepository.count();
    }

    /**
     * Get insurance with effective to before certain date
     *
     * @param effectiveTo
     * @return
     */
    public List<Insurance> getAllByEffectiveToBefore(Date effectiveTo) {
        return insuranceRepository.findAllByEffectiveToBeforeAndActiveTrue(effectiveTo);
    }
}
