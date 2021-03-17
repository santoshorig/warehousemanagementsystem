package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderClient;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.TenderClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderClientServiceImpl implements TenderClientService {

    @Autowired
    private TenderClientRepository tenderClientRepository;

    /**
     * Get tender client
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TenderClient> get(Integer id) {
        return tenderClientRepository.findById(id);
    }

    /**
     * Edit tender client
     *
     * @param tenderClient
     * @return
     */
    @Override
    public TenderClient edit(TenderClient tenderClient) {
        return tenderClientRepository.save(tenderClient);
    }

    /**
     * Delete tender client
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        tenderClientRepository.deleteById(id);
    }

    /**
     * Get all tender client basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<TenderClient> getAll(Integer pageNumber, Integer pageSize) {
        return tenderClientRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all tender client
     *
     * @return
     */
    @Override
    public List<TenderClient> getAll() {
        return tenderClientRepository.findAll();
    }

    /**
     * Get tender client count
     *
     * @return
     */
    public Long count() {
        return tenderClientRepository.count();
    }
}
