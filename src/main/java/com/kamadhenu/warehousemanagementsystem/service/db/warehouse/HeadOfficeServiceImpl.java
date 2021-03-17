package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.HeadOffice;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.HeadOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeadOfficeServiceImpl implements HeadOfficeService {

    @Autowired
    private HeadOfficeRepository headOfficeRepository;

    /**
     * Get head office
     *
     * @param id
     * @return
     */
    @Override
    public Optional<HeadOffice> get(Integer id) {
        return headOfficeRepository.findById(id);
    }

    /**
     * Edit head office
     *
     * @param headOffice
     * @return
     */
    @Override
    public HeadOffice edit(HeadOffice headOffice) {
        return headOfficeRepository.save(headOffice);
    }

    /**
     * Delete head office
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        headOfficeRepository.deleteById(id);
    }

    /**
     * Get all head office basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<HeadOffice> getAll(Integer pageNumber, Integer pageSize) {
        return headOfficeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all head office
     *
     * @return
     */
    @Override
    public List<HeadOffice> getAll() {
        return headOfficeRepository.findAll();
    }

    /**
     * Get head office count
     *
     * @return
     */
    public Long count() {
        return headOfficeRepository.count();
    }
}
