package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import com.kamadhenu.warehousemanagementsystem.repository.location.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    /**
     * Get district
     *
     * @param id
     * @return
     */
    @Override
    public Optional<District> get(Integer id) {
        return districtRepository.findById(id);
    }

    /**
     * Edit district
     *
     * @param district
     * @return
     */
    @Override
    public District edit(District district) {
        return districtRepository.save(district);
    }

    /**
     * Delete district
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        districtRepository.deleteById(id);
    }

    /**
     * Get all district basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<District> getAll(Integer pageNumber, Integer pageSize) {
        return districtRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all district
     *
     * @return
     */
    @Override
    public List<District> getAll() {
        return districtRepository.findAll();
    }

    /**
     * Get district count
     *
     * @return
     */
    public Long count() {
        return districtRepository.count();
    }

    /**
     * Get all district by state
     *
     * @param state
     * @return
     */
    public List<District> getByState(State state) {
        return districtRepository.getByState(state);
    }
}
