package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TakeoverType;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TakeoverTypeRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TakeoverTypeServiceImpl implements TakeoverTypeService {

    @Autowired
    private TakeoverTypeRepository takeoverTypeRepository;

    @Autowired
    private HelperService helperService;

    /**
     * Get takeover type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TakeoverType> get(Integer id) {
        return takeoverTypeRepository.findById(id);
    }

    /**
     * Edit takeover type
     *
     * @param takeoverType
     * @return
     */
    @Override
    public TakeoverType edit(TakeoverType takeoverType) {
        return takeoverTypeRepository.save(takeoverType);
    }

    /**
     * Delete takeover type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        takeoverTypeRepository.deleteById(id);
    }

    /**
     * Get all takeover type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TakeoverType> getAll(Integer pageNumber, Integer pageSize) {
        return takeoverTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all takeover type
     *
     * @return
     */
    @Override
    public List<TakeoverType> getAll() {
        return takeoverTypeRepository.findAll();
    }

    /**
     * Get takeover type count
     *
     * @return
     */
    public Long count() {
        return takeoverTypeRepository.count();
    }

    /**
     * Get takeover type by business type
     *
     * @return
     */
    public List<TakeoverType> getByBusinessType() {
        return takeoverTypeRepository.findByBusinessTypeIn(
                helperService.getUserBusinessType()
        );
    }
}
