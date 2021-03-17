package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.StatusOfParty;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.StatusOfPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusOfPartyServiceImpl implements StatusOfPartyService {

    @Autowired
    private StatusOfPartyRepository statusOfPartyRepository;

    /**
     * Get status of party
     *
     * @param id
     * @return
     */
    @Override
    public Optional<StatusOfParty> get(Integer id) {
        return statusOfPartyRepository.findById(id);
    }

    /**
     * Edit status of party
     *
     * @param statusOfParty
     * @return
     */
    @Override
    public StatusOfParty edit(StatusOfParty statusOfParty) {
        return statusOfPartyRepository.save(statusOfParty);
    }

    /**
     * Delete status of party
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        statusOfPartyRepository.deleteById(id);
    }

    /**
     * Get all status of party basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<StatusOfParty> getAll(Integer pageNumber, Integer pageSize) {
        return statusOfPartyRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all status of party
     *
     * @return
     */
    @Override
    public List<StatusOfParty> getAll() {
        return statusOfPartyRepository.findAll();
    }

    /**
     * Get status of party count
     *
     * @return
     */
    public Long count() {
        return statusOfPartyRepository.count();
    }
}
