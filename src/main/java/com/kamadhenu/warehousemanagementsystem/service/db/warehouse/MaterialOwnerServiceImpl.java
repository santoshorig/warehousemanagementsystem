package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.MaterialOwner;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.MaterialOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialOwnerServiceImpl implements MaterialOwnerService {

    @Autowired
    private MaterialOwnerRepository materialOwnerRepository;

    /**
     * Get material owner
     *
     * @param id
     * @return
     */
    @Override
    public Optional<MaterialOwner> get(Integer id) {
        return materialOwnerRepository.findById(id);
    }

    /**
     * Edit material owner
     *
     * @param materialOwner
     * @return
     */
    @Override
    public MaterialOwner edit(MaterialOwner materialOwner) {
        return materialOwnerRepository.save(materialOwner);
    }

    /**
     * Delete material owner
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        materialOwnerRepository.deleteById(id);
    }

    /**
     * Get all material owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<MaterialOwner> getAll(Integer pageNumber, Integer pageSize) {
        return materialOwnerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all material owner
     *
     * @return
     */
    @Override
    public List<MaterialOwner> getAll() {
        return materialOwnerRepository.findAll();
    }

    /**
     * Get material owner count
     *
     * @return
     */
    public Long count() {
        return materialOwnerRepository.count();
    }
}
