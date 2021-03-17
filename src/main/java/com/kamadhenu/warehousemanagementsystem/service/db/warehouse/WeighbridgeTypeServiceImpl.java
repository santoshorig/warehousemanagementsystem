package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WeighbridgeType;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.WeighbridgeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeighbridgeTypeServiceImpl implements WeighbridgeTypeService {

    @Autowired
    private WeighbridgeTypeRepository weighbridgeTypeRepository;

    /**
     * Get weighbridge type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<WeighbridgeType> get(Integer id) {
        return weighbridgeTypeRepository.findById(id);
    }

    /**
     * Edit weighbridge type
     *
     * @param weighbridgeType
     * @return
     */
    @Override
    public WeighbridgeType edit(WeighbridgeType weighbridgeType) {
        return weighbridgeTypeRepository.save(weighbridgeType);
    }

    /**
     * Delete weighbridge type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        weighbridgeTypeRepository.deleteById(id);
    }

    /**
     * Get all weighbridge type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<WeighbridgeType> getAll(Integer pageNumber, Integer pageSize) {
        return weighbridgeTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all weighbridge type
     *
     * @return
     */
    @Override
    public List<WeighbridgeType> getAll() {
        return weighbridgeTypeRepository.findAll();
    }

    /**
     * Get weighbridge type count
     *
     * @return
     */
    public Long count() {
        return weighbridgeTypeRepository.count();
    }
}
