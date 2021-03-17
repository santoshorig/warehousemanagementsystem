package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityInsurance;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderCommodityInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderCommodityInsuranceServiceImpl implements TenderCommodityInsuranceService {

    @Autowired
    private TenderCommodityInsuranceRepository tenderCommodityInsuranceRepository;

    /**
     * Get tender commodity insurance
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderCommodityInsurance> get(Integer id) {
        return tenderCommodityInsuranceRepository.findById(id);
    }

    /**
     * Edit tender commodity insurance
     *
     * @param tenderCommodityInsurance
     * @return
     */
    @Override
    public TenderCommodityInsurance edit(TenderCommodityInsurance tenderCommodityInsurance) {
        return tenderCommodityInsuranceRepository.save(tenderCommodityInsurance);
    }

    /**
     * Delete tender commodity insurance
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderCommodityInsuranceRepository.deleteById(id);
    }

    /**
     * Get all tender commodity insurance basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderCommodityInsurance> getAll(Integer pageNumber, Integer pageSize) {
        return tenderCommodityInsuranceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender commodity insurance
     *
     * @return
     */
    @Override
    public List<TenderCommodityInsurance> getAll() {
        return tenderCommodityInsuranceRepository.findAll();
    }

    /**
     * Get tender commodity insurance count
     *
     * @return
     */
    public Long count() {
        return tenderCommodityInsuranceRepository.count();
    }

    /**
     * Get tender commodity insurance
     *
     * @param tender
     * @return
     */
    public List<TenderCommodityInsurance> getByTender(Tender tender) {
        return tenderCommodityInsuranceRepository.findByTender(tender);
    }
}
