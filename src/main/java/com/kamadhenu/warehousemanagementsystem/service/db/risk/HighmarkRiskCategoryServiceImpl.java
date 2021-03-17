package com.kamadhenu.warehousemanagementsystem.service.db.risk;

import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;
import com.kamadhenu.warehousemanagementsystem.repository.risk.HighmarkRiskCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HighmarkRiskCategoryServiceImpl implements HighmarkRiskCategoryService {

    @Autowired
    private HighmarkRiskCategoryRepository highmarkRiskCategoryRepository;

    /**
     * Get highmark risk category
     *
     * @param id
     * @return
     */
    @Override
    public Optional<HighmarkRiskCategory> get(Integer id) {
        return highmarkRiskCategoryRepository.findById(id);
    }

    /**
     * Edit highmark risk category
     *
     * @param highmarkRiskCategory
     * @return
     */
    @Override
    public HighmarkRiskCategory edit(HighmarkRiskCategory highmarkRiskCategory) {
        return highmarkRiskCategoryRepository.save(highmarkRiskCategory);
    }

    /**
     * Delete highmark risk category
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        highmarkRiskCategoryRepository.deleteById(id);
    }

    /**
     * Get all highmark risk category basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<HighmarkRiskCategory> getAll(Integer pageNumber, Integer pageSize) {
        return highmarkRiskCategoryRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all highmark risk category
     *
     * @return
     */
    @Override
    public List<HighmarkRiskCategory> getAll() {
        return highmarkRiskCategoryRepository.findAll();
    }

    /**
     * Get highmark risk category count
     *
     * @return
     */
    public Long count() {
        return highmarkRiskCategoryRepository.count();
    }
}
