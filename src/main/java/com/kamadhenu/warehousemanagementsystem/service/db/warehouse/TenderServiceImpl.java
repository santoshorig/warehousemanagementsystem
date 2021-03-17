package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderServiceImpl implements TenderService {

    @Autowired
    private TenderRepository tenderRepository;

    /**
     * Get tender
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Tender> get(Integer id) {
        return tenderRepository.findById(id);
    }

    /**
     * Edit tender
     *
     * @param tender
     * @return
     */
    @Override
    public Tender edit(Tender tender) {
        return tenderRepository.save(tender);
    }

    /**
     * Delete tender
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderRepository.deleteById(id);
    }

    /**
     * Get all tender basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Tender> getAll(Integer pageNumber, Integer pageSize) {
        return tenderRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender
     *
     * @return
     */
    @Override
    public List<Tender> getAll() {
        return tenderRepository.findAll();
    }

    /**
     * Get tender count
     *
     * @return
     */
    public Long count() {
        return tenderRepository.count();
    }
}
