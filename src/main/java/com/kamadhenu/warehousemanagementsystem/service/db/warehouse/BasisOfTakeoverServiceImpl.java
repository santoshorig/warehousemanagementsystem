package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.BasisOfTakeoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasisOfTakeoverServiceImpl implements BasisOfTakeoverService {

    @Autowired
    private BasisOfTakeoverRepository basisOfTakeoverRepository;

    /**
     * Get basis of takeover
     *
     * @param id
     * @return
     */
    @Override
    public Optional<BasisOfTakeover> get(Integer id) {
        return basisOfTakeoverRepository.findById(id);
    }

    /**
     * Edit basis of takeover
     *
     * @param basisOfTakeover
     * @return
     */
    @Override
    public BasisOfTakeover edit(BasisOfTakeover basisOfTakeover) {
        return basisOfTakeoverRepository.save(basisOfTakeover);
    }

    /**
     * Delete basis of takeover
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        basisOfTakeoverRepository.deleteById(id);
    }

    /**
     * Get all basis of takeover basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<BasisOfTakeover> getAll(Integer pageNumber, Integer pageSize) {
        return basisOfTakeoverRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all basis of takeover
     *
     * @return
     */
    @Override
    public List<BasisOfTakeover> getAll() {
        return basisOfTakeoverRepository.findAll();
    }

    /**
     * Get basis of takeover count
     *
     * @return
     */
    public Long count() {
        return basisOfTakeoverRepository.count();
    }
}
