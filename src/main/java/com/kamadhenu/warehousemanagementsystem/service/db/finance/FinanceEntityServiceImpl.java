package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.FinanceEntity;
import com.kamadhenu.warehousemanagementsystem.repository.finance.FinanceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceEntityServiceImpl implements FinanceEntityService {

    @Autowired
    private FinanceEntityRepository financeEntityRepository;

    /**
     * Get finance entity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<FinanceEntity> get(Integer id) {
        return financeEntityRepository.findById(id);
    }

    /**
     * Edit finance entity
     *
     * @param financeEntity
     * @return
     */
    @Override
    public FinanceEntity edit(FinanceEntity financeEntity) {
        return financeEntityRepository.save(financeEntity);
    }

    /**
     * Delete finance entity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        financeEntityRepository.deleteById(id);
    }

    /**
     * Get all finance entity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<FinanceEntity> getAll(Integer pageNumber, Integer pageSize) {
        return financeEntityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all finance entity
     *
     * @return
     */
    @Override
    public List<FinanceEntity> getAll() {
        return financeEntityRepository.findAll();
    }

    /**
     * Get finance entity count
     *
     * @return
     */
    public Long count() {
        return financeEntityRepository.count();
    }
}
