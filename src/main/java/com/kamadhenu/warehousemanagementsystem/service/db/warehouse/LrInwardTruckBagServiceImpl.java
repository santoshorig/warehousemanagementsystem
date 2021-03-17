package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.LrInwardTruckBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LrInwardTruckBagServiceImpl implements LrInwardTruckBagService {

    @Autowired
    private LrInwardTruckBagRepository lrInwardTruckBagRepository;

    /**
     * Get lr inward truck bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<LrInwardTruckBag> get(Integer id) {
        return lrInwardTruckBagRepository.findById(id);
    }

    /**
     * Edit lr inward truck bag
     *
     * @param lrRemark
     * @return
     */
    @Override
    public LrInwardTruckBag edit(LrInwardTruckBag lrRemark) {
        return lrInwardTruckBagRepository.save(lrRemark);
    }

    /**
     * Delete lr inward truck bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        lrInwardTruckBagRepository.deleteById(id);
    }

    /**
     * Get all lr inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<LrInwardTruckBag> getAll(Integer pageNumber, Integer pageSize) {
        return lrInwardTruckBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all lr inward truck bag
     *
     * @return
     */
    @Override
    public List<LrInwardTruckBag> getAll() {
        return lrInwardTruckBagRepository.findAll();
    }

    /**
     * Get lr inward truck bag count
     *
     * @return
     */
    public Long count() {
        return lrInwardTruckBagRepository.count();
    }

    /**
     * Get lr inward truck bag
     *
     * @param lr
     * @return
     */
    @Override
    public List<LrInwardTruckBag> getByLr(Lr lr) {
        return lrInwardTruckBagRepository.findByLr(lr);
    }
}
