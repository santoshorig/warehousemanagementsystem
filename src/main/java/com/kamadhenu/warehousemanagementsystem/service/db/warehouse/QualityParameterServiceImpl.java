package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.QualityParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualityParameterServiceImpl implements QualityParameterService {

    @Autowired
    private QualityParameterRepository qualityParameterRepository;

    /**
     * Get quality parameter
     *
     * @param id
     * @return
     */
    @Override
    public Optional<QualityParameter> get(Integer id) {
        return qualityParameterRepository.findById(id);
    }

    /**
     * Edit quality parameter
     *
     * @param qualityParameter
     * @return
     */
    @Override
    public QualityParameter edit(QualityParameter qualityParameter) {
        return qualityParameterRepository.save(qualityParameter);
    }

    /**
     * Delete quality parameter
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        qualityParameterRepository.deleteById(id);
    }

    /**
     * Get all quality parameter basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<QualityParameter> getAll(Integer pageNumber, Integer pageSize) {
        return qualityParameterRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all quality parameter
     *
     * @return
     */
    @Override
    public List<QualityParameter> getAll() {
        return qualityParameterRepository.findAll();
    }

    /**
     * Get quality parameter count
     *
     * @return
     */
    public Long count() {
        return qualityParameterRepository.count();
    }
}
