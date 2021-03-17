package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderCommodityAcceptanceLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderCommodityAcceptanceLimitServiceImpl implements TenderCommodityAcceptanceLimitService {

    @Autowired
    private TenderCommodityAcceptanceLimitRepository tenderCommodityAcceptanceLimitRepository;

    /**
     * Get tender commodity acceptance limit
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderCommodityAcceptanceLimit> get(Integer id) {
        return tenderCommodityAcceptanceLimitRepository.findById(id);
    }

    /**
     * Edit tender commodity acceptance limit
     *
     * @param tenderCommodityAcceptanceLimit
     * @return
     */
    @Override
    public TenderCommodityAcceptanceLimit edit(TenderCommodityAcceptanceLimit tenderCommodityAcceptanceLimit) {
        return tenderCommodityAcceptanceLimitRepository.save(tenderCommodityAcceptanceLimit);
    }

    /**
     * Delete tender commodity acceptance limit
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderCommodityAcceptanceLimitRepository.deleteById(id);
    }

    /**
     * Get all tender commodity acceptance limit basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderCommodityAcceptanceLimit> getAll(Integer pageNumber, Integer pageSize) {
        return tenderCommodityAcceptanceLimitRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender commodity acceptance limit
     *
     * @return
     */
    @Override
    public List<TenderCommodityAcceptanceLimit> getAll() {
        return tenderCommodityAcceptanceLimitRepository.findAll();
    }

    /**
     * Get tender commodity acceptance limit count
     *
     * @return
     */
    public Long count() {
        return tenderCommodityAcceptanceLimitRepository.count();
    }

    /**
     * Get by tender
     *
     * @param tender
     * @return
     */
    public List<TenderCommodityAcceptanceLimit> getByTender(Tender tender) {
        return tenderCommodityAcceptanceLimitRepository.findByTender(tender);
    }
}
