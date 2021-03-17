package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Manpower;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.ManpowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManpowerServiceImpl implements ManpowerService {

    @Autowired
    private ManpowerRepository manpowerRepository;

    /**
     * Get manpower
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Manpower> get(Integer id) {
        return manpowerRepository.findById(id);
    }

    /**
     * Edit manpower
     *
     * @param manpower
     * @return
     */
    @Override
    public Manpower edit(Manpower manpower) {
        return manpowerRepository.save(manpower);
    }

    /**
     * Delete manpower
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        manpowerRepository.deleteById(id);
    }

    /**
     * Get all manpower basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Manpower> getAll(Integer pageNumber, Integer pageSize) {
        return manpowerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all manpower
     *
     * @return
     */
    @Override
    public List<Manpower> getAll() {
        return manpowerRepository.findAll();
    }

    /**
     * Get manpower count
     *
     * @return
     */
    public Long count() {
        return manpowerRepository.count();
    }
}
