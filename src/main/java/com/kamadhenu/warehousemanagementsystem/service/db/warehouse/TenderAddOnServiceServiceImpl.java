package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderAddOnService;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderAddOnServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderAddOnServiceServiceImpl implements TenderAddOnServiceService {

    @Autowired
    private TenderAddOnServiceRepository tenderAddOnServiceRepository;

    /**
     * Get tender add on service
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderAddOnService> get(Integer id) {
        return tenderAddOnServiceRepository.findById(id);
    }

    /**
     * Edit tender add on service
     *
     * @param tenderAddOnService
     * @return
     */
    @Override
    public TenderAddOnService edit(TenderAddOnService tenderAddOnService) {
        return tenderAddOnServiceRepository.save(tenderAddOnService);
    }

    /**
     * Delete tender add on service
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderAddOnServiceRepository.deleteById(id);
    }

    /**
     * Get all tender add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderAddOnService> getAll(Integer pageNumber, Integer pageSize) {
        return tenderAddOnServiceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender add on service
     *
     * @return
     */
    @Override
    public List<TenderAddOnService> getAll() {
        return tenderAddOnServiceRepository.findAll();
    }

    /**
     * Get tender add on service count
     *
     * @return
     */
    public Long count() {
        return tenderAddOnServiceRepository.count();
    }

    /**
     * Get tender add on service
     *
     * @param tender
     * @return
     */
    public List<TenderAddOnService> getByTender(Tender tender) {
        return tenderAddOnServiceRepository.findByTender(tender);
    }
}
