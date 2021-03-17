package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodity;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderCommodityServiceImpl implements TenderCommodityService {

    @Autowired
    private TenderCommodityRepository tenderCommodityRepository;

    /**
     * Get tender commodity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderCommodity> get(Integer id) {
        return tenderCommodityRepository.findById(id);
    }

    /**
     * Edit tender commodity
     *
     * @param tenderCommodity
     * @return
     */
    @Override
    public TenderCommodity edit(TenderCommodity tenderCommodity) {
        return tenderCommodityRepository.save(tenderCommodity);
    }

    /**
     * Delete tender commodity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderCommodityRepository.deleteById(id);
    }

    /**
     * Get all tender commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderCommodity> getAll(Integer pageNumber, Integer pageSize) {
        return tenderCommodityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender commodity
     *
     * @return
     */
    @Override
    public List<TenderCommodity> getAll() {
        return tenderCommodityRepository.findAll();
    }

    /**
     * Get tender commodity count
     *
     * @return
     */
    public Long count() {
        return tenderCommodityRepository.count();
    }

    /**
     * Get tender commodity
     *
     * @param tender
     * @return
     */
    public List<TenderCommodity> getByTender(Tender tender) {
        return tenderCommodityRepository.findByTender(tender);
    }
}
