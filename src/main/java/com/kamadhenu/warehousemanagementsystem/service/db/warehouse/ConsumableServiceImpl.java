package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Consumable;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.ConsumableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumableServiceImpl implements ConsumableService {

    @Autowired
    private ConsumableRepository consumableRepository;

    /**
     * Get consumable
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Consumable> get(Integer id) {
        return consumableRepository.findById(id);
    }

    /**
     * Edit consumable
     *
     * @param consumable
     * @return
     */
    @Override
    public Consumable edit(Consumable consumable) {
        return consumableRepository.save(consumable);
    }

    /**
     * Delete consumable
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        consumableRepository.deleteById(id);
    }

    /**
     * Get all consumable basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Consumable> getAll(Integer pageNumber, Integer pageSize) {
        return consumableRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all consumable
     *
     * @return
     */
    @Override
    public List<Consumable> getAll() {
        return consumableRepository.findAll();
    }

    /**
     * Get consumable count
     *
     * @return
     */
    public Long count() {
        return consumableRepository.count();
    }
}
