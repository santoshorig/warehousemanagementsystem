package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.repository.general.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = {"commodity"})
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    /**
     * Get commodity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Commodity> get(Integer id) {
        return commodityRepository.findById(id);
    }

    /**
     * Edit commodity
     *
     * @param commodity
     * @return
     */
    @Override
    @CachePut
    public Commodity edit(Commodity commodity) {
        return commodityRepository.save(commodity);
    }

    /**
     * Delete commodity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        commodityRepository.deleteById(id);
    }

    /**
     * Get all commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable
    public List<Commodity> getAll(Integer pageNumber, Integer pageSize) {
        return commodityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all commodity
     *
     * @return
     */
    @Override
    @Cacheable
    public List<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    /**
     * Get commodity count
     *
     * @return
     */
    public Long count() {
        return commodityRepository.count();
    }
}
