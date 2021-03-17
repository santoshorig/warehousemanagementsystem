package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.OutwardMadeUpBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutwardMadeUpBagServiceImpl implements OutwardMadeUpBagService {

    @Autowired
    private OutwardMadeUpBagRepository outwardMadeUpBagRepository;

    /**
     * Get outward made up bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<OutwardMadeUpBag> get(Integer id) {
        return outwardMadeUpBagRepository.findById(id);
    }

    /**
     * Edit outward made up bag
     *
     * @param outwardMadeUpBag
     * @return
     */
    @Override
    public OutwardMadeUpBag edit(OutwardMadeUpBag outwardMadeUpBag) {
        return outwardMadeUpBagRepository.save(outwardMadeUpBag);
    }

    /**
     * Delete outward made up bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outwardMadeUpBagRepository.deleteById(id);
    }

    /**
     * Get all outward made up bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<OutwardMadeUpBag> getAll(Integer pageNumber, Integer pageSize) {
        return outwardMadeUpBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all outward made up bag
     *
     * @return
     */
    @Override
    public List<OutwardMadeUpBag> getAll() {
        return outwardMadeUpBagRepository.findAll();
    }

    /**
     * Get outward made up bag count
     *
     * @return
     */
    public Long count() {
        return outwardMadeUpBagRepository.count();
    }

    /**
     * Get outward made up bag by outward
     *
     * @param outward
     * @return
     */
    public List<OutwardMadeUpBag> getByOutward(Outward outward) {
        return outwardMadeUpBagRepository.findByOutward(outward);
    }
}
