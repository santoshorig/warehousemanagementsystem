package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityLab;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.QualityLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualityLabServiceImpl implements QualityLabService {

    @Autowired
    private QualityLabRepository qualityLabRepository;

    /**
     * Get qualityLab
     *
     * @param id
     * @return
     */
    @Override
    public Optional<QualityLab> get(Integer id) {
        return qualityLabRepository.findById(id);
    }

    /**
     * Edit qualityLab
     *
     * @param qualityLab
     * @return
     */
    @Override
    public QualityLab edit(QualityLab qualityLab) {
        return qualityLabRepository.save(qualityLab);
    }

    /**
     * Delete qualityLab
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        qualityLabRepository.deleteById(id);
    }

    /**
     * Get all qualityLab basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<QualityLab> getAll(Integer pageNumber, Integer pageSize) {
        return qualityLabRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all qualityLab
     *
     * @return
     */
    @Override
    public List<QualityLab> getAll() {
        return qualityLabRepository.findAll();
    }

    /**
     * Get qualityLab count
     *
     * @return
     */
    public Long count() {
        return qualityLabRepository.count();
    }

    /**
     * Get by location
     *
     * @param location
     * @return
     */
    public List<QualityLab> getByLocation(Location location) {
        return qualityLabRepository.getByLocation(location);
    }
}
