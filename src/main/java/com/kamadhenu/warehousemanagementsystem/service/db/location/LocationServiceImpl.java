package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.repository.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Get location
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Location> get(Integer id) {
        return locationRepository.findById(id);
    }

    /**
     * Edit location
     *
     * @param location
     * @return
     */
    @Override
    public Location edit(Location location) {
        return locationRepository.save(location);
    }

    /**
     * Delete location
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        locationRepository.deleteById(id);
    }

    /**
     * Get all location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Location> getAll(Integer pageNumber, Integer pageSize) {
        return locationRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all location
     *
     * @return
     */
    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    /**
     * Get location count
     *
     * @return
     */
    public Long count() {
        return locationRepository.count();
    }

    /**
     * Get all locations by districts
     *
     * @param districtList
     * @return
     */
    public List<Location> getByDistricts(List<District> districtList) {
        return locationRepository.getByDistrictIn(districtList);
    }
}
