package com.kamadhenu.warehousemanagementsystem.service.db.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.Margin;
import com.kamadhenu.warehousemanagementsystem.repository.finance.MarginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarginServiceImpl implements MarginService {

    @Autowired
    private MarginRepository marginRepository;

    /**
     * Get margin
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Margin> get(Integer id) {
        return marginRepository.findById(id);
    }

    /**
     * Edit margin
     *
     * @param margin
     * @return
     */
    @Override
    public Margin edit(Margin margin) {
        return marginRepository.save(margin);
    }

    /**
     * Delete margin
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        marginRepository.deleteById(id);
    }

    /**
     * Get all margin basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Margin> getAll(Integer pageNumber, Integer pageSize) {
        return marginRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all margin
     *
     * @return
     */
    @Override
    public List<Margin> getAll() {
        return marginRepository.findAll();
    }

    /**
     * Get margin count
     *
     * @return
     */
    public Long count() {
        return marginRepository.count();
    }
}
