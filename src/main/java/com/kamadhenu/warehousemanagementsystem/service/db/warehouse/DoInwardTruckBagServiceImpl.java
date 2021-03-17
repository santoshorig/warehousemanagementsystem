package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.DoInwardTruckBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoInwardTruckBagServiceImpl implements DoInwardTruckBagService {

    @Autowired
    private DoInwardTruckBagRepository doInwardTruckBagRepository;

    /**
     * Get do inward truck bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<DoInwardTruckBag> get(Integer id) {
        return doInwardTruckBagRepository.findById(id);
    }

    /**
     * Edit do inward truck bag
     *
     * @param doRemark
     * @return
     */
    @Override
    public DoInwardTruckBag edit(DoInwardTruckBag doRemark) {
        return doInwardTruckBagRepository.save(doRemark);
    }

    /**
     * Delete do inward truck bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        doInwardTruckBagRepository.deleteById(id);
    }

    /**
     * Get all do inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<DoInwardTruckBag> getAll(Integer pageNumber, Integer pageSize) {
        return doInwardTruckBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all do inward truck bag
     *
     * @return
     */
    @Override
    public List<DoInwardTruckBag> getAll() {
        return doInwardTruckBagRepository.findAll();
    }

    /**
     * Get do inward truck bag count
     *
     * @return
     */
    public Long count() {
        return doInwardTruckBagRepository.count();
    }

    /**
     * Get do inward truck bag
     *
     * @param doModel
     * @return
     */
    @Override
    public List<DoInwardTruckBag> getByDo(Do doModel) {
        return doInwardTruckBagRepository.findByDoModel(doModel);
    }

    /**
     * Get for outward by do inward truck bags basis do
     *
     * @param doModel
     * @return
     */
    public List<DoInwardTruckBag> getForOutwardByDo(Do doModel) {
        return doInwardTruckBagRepository.findByDoModelAndOutward(doModel, false);
    }
}
