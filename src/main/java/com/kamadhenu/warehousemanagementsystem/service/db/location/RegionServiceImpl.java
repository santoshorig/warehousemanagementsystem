package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;
import com.kamadhenu.warehousemanagementsystem.repository.location.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    /**
     * Get region
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Region> get(Integer id) {
        return regionRepository.findById(id);
    }

    /**
     * Edit region
     *
     * @param region
     * @return
     */
    @Override
    public Region edit(Region region) {
        return regionRepository.save(region);
    }

    /**
     * Delete region
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        regionRepository.deleteById(id);
    }

    /**
     * Get all region basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Region> getAll(Integer pageNumber, Integer pageSize) {
        return regionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all region
     *
     * @return
     */
    @Override
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    /**
     * Get region count
     *
     * @return
     */
    public Long count() {
        return regionRepository.count();
    }
}
