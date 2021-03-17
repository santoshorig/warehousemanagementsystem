package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.repository.general.BusinessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    /**
     * Get business type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<BusinessType> get(Integer id) {
        return businessTypeRepository.findById(id);
    }

    /**
     * Edit business type
     *
     * @param businessType
     * @return
     */
    @Override
    public BusinessType edit(BusinessType businessType) {
        return businessTypeRepository.save(businessType);
    }

    /**
     * Delete business type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        businessTypeRepository.deleteById(id);
    }

    /**
     * Get all business type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<BusinessType> getAll(Integer pageNumber, Integer pageSize) {
        return businessTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all business type
     *
     * @return
     */
    @Override
    public List<BusinessType> getAll() {
        return businessTypeRepository.findAll();
    }

    /**
     * Get business type count
     *
     * @return
     */
    public Long count() {
        return businessTypeRepository.count();
    }
}
