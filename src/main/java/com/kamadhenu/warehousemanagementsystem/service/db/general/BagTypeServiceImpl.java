package com.kamadhenu.warehousemanagementsystem.service.db.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.repository.general.BagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BagTypeServiceImpl implements BagTypeService {

    @Autowired
    private BagTypeRepository bagTypeRepository;

    /**
     * Get bag type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<BagType> get(Integer id) {
        return bagTypeRepository.findById(id);
    }

    /**
     * Edit bag type
     *
     * @param bagType
     * @return
     */
    @Override
    public BagType edit(BagType bagType) {
        return bagTypeRepository.save(bagType);
    }

    /**
     * Delete bag type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        bagTypeRepository.deleteById(id);
    }

    /**
     * Get all bag type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<BagType> getAll(Integer pageNumber, Integer pageSize) {
        return bagTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all bag type
     *
     * @return
     */
    @Override
    public List<BagType> getAll() {
        return bagTypeRepository.findAll();
    }

    /**
     * Get bag type count
     *
     * @return
     */
    public Long count() {
        return bagTypeRepository.count();
    }
}
