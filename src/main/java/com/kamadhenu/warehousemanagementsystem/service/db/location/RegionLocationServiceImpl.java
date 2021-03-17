package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.repository.location.RegionLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionLocationServiceImpl implements RegionLocationService {

    @Autowired
    private RegionLocationRepository regionLocationRepository;

    /**
     * Get region location
     *
     * @param id
     * @return
     */
    @Override
    public Optional<RegionLocation> get(Integer id) {
        return regionLocationRepository.findById(id);
    }

    /**
     * Edit region location
     *
     * @param regionLocation
     * @return
     */
    @Override
    public RegionLocation edit(RegionLocation regionLocation) {
        return regionLocationRepository.save(regionLocation);
    }

    /**
     * Delete region location
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        regionLocationRepository.deleteById(id);
    }

    /**
     * Get all region location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<RegionLocation> getAll(Integer pageNumber, Integer pageSize) {
        return regionLocationRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all region location
     *
     * @return
     */
    @Override
    public List<RegionLocation> getAll() {
        return regionLocationRepository.findAll();
    }

    /**
     * Get region location count
     *
     * @return
     */
    public Long count() {
        return regionLocationRepository.count();
    }
}
