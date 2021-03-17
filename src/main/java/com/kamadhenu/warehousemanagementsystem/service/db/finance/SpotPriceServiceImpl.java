package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.SpotPrice;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.repository.finance.SpotPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpotPriceServiceImpl implements SpotPriceService {

    @Autowired
    private SpotPriceRepository spotPriceRepository;

    /**
     * Get spot price
     *
     * @param id
     * @return
     */
    @Override
    public Optional<SpotPrice> get(Integer id) {
        return spotPriceRepository.findById(id);
    }

    /**
     * Edit spot price
     *
     * @param spotPrice
     * @return
     */
    @Override
    public SpotPrice edit(SpotPrice spotPrice) {
        return spotPriceRepository.save(spotPrice);
    }

    /**
     * Delete spot price
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        spotPriceRepository.deleteById(id);
    }

    /**
     * Get all spot price basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<SpotPrice> getAll(Integer pageNumber, Integer pageSize) {
        return spotPriceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all spot price
     *
     * @return
     */
    @Override
    public List<SpotPrice> getAll() {
        return spotPriceRepository.findAll();
    }

    /**
     * Get spot price count
     *
     * @return
     */
    public Long count() {
        return spotPriceRepository.count();
    }

    /**
     * Get spot price by commodity and from date and to date and location
     *
     * @param commodity
     * @param fromDate
     * @param toDate
     * @param location
     * @return
     */
    public List<SpotPrice> getByCommodityAndFromDateAndToDateAndLocation(
            Commodity commodity,
            Date fromDate,
            Date toDate,
            Location location
    ) {
        return spotPriceRepository
                .findByCommodityAndFromDateLessThanEqualAndToDateGreaterThanEqualAndLocation(
                        commodity,
                        fromDate,
                        toDate,
                        location
                );
    }
}
