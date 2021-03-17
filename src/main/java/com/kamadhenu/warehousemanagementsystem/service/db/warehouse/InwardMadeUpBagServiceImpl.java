package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardMadeUpBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InwardMadeUpBagServiceImpl implements InwardMadeUpBagService {

    @Autowired
    private InwardMadeUpBagRepository inwardMadeUpBagRepository;

    /**
     * Get inward made up bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardMadeUpBag> get(Integer id) {
        return inwardMadeUpBagRepository.findById(id);
    }

    /**
     * Edit inward made up bag
     *
     * @param inwardMadeUpBag
     * @return
     */
    @Override
    public InwardMadeUpBag edit(InwardMadeUpBag inwardMadeUpBag) {
        return inwardMadeUpBagRepository.save(inwardMadeUpBag);
    }

    /**
     * Delete inward made up bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardMadeUpBagRepository.deleteById(id);
    }

    /**
     * Get all inward made up bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardMadeUpBag> getAll(Integer pageNumber, Integer pageSize) {
        return inwardMadeUpBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward made up bag
     *
     * @return
     */
    @Override
    public List<InwardMadeUpBag> getAll() {
        return inwardMadeUpBagRepository.findAll();
    }

    /**
     * Get inward made up bag count
     *
     * @return
     */
    public Long count() {
        return inwardMadeUpBagRepository.count();
    }

    /**
     * Get inward made up bag by inward
     *
     * @param inward
     * @return
     */
    public List<InwardMadeUpBag> getByInward(Inward inward) {
        return inwardMadeUpBagRepository.findByInward(inward);
    }

    /**
     * Get inward made up bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    public List<InwardMadeUpBag> getByWarehouseStack(WarehouseStack warehouseStack) {
        return inwardMadeUpBagRepository.findByWarehouseStack(warehouseStack);
    }

    /**
     * Get inward made up bag for outward by inward
     *
     * @param inward
     * @return
     */
    public List<InwardMadeUpBag> getForOutwardByInward(Inward inward) {
        return inwardMadeUpBagRepository.findByInwardAndOutward(inward, false);
    }
}
