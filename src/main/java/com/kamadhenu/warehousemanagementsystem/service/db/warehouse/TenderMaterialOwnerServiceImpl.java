package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderMaterialOwner;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderMaterialOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderMaterialOwnerServiceImpl implements TenderMaterialOwnerService {

    @Autowired
    private TenderMaterialOwnerRepository tenderMaterialOwnerRepository;

    /**
     * Get tender material owner
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderMaterialOwner> get(Integer id) {
        return tenderMaterialOwnerRepository.findById(id);
    }

    /**
     * Edit tender material owner
     *
     * @param tenderMaterialOwner
     * @return
     */
    @Override
    public TenderMaterialOwner edit(TenderMaterialOwner tenderMaterialOwner) {
        return tenderMaterialOwnerRepository.save(tenderMaterialOwner);
    }

    /**
     * Delete tender material owner
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderMaterialOwnerRepository.deleteById(id);
    }

    /**
     * Get all tender material owner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderMaterialOwner> getAll(Integer pageNumber, Integer pageSize) {
        return tenderMaterialOwnerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender material owner
     *
     * @return
     */
    @Override
    public List<TenderMaterialOwner> getAll() {
        return tenderMaterialOwnerRepository.findAll();
    }

    /**
     * Get tender material owner count
     *
     * @return
     */
    public Long count() {
        return tenderMaterialOwnerRepository.count();
    }
}
