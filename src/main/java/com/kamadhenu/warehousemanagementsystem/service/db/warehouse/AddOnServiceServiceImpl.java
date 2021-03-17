package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.AddOnServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddOnServiceServiceImpl implements AddOnServiceService {

    @Autowired
    private AddOnServiceRepository addOnServiceRepository;

    /**
     * Get add on service
     *
     * @param id
     * @return
     */
    @Override
    public Optional<AddOnService> get(Integer id) {
        return addOnServiceRepository.findById(id);
    }

    /**
     * Edit add on service
     *
     * @param addOnService
     * @return
     */
    @Override
    public AddOnService edit(AddOnService addOnService) {
        return addOnServiceRepository.save(addOnService);
    }

    /**
     * Delete add on service
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        addOnServiceRepository.deleteById(id);
    }

    /**
     * Get all add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<AddOnService> getAll(Integer pageNumber, Integer pageSize) {
        return addOnServiceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all add on service
     *
     * @return
     */
    @Override
    public List<AddOnService> getAll() {
        return addOnServiceRepository.findAll();
    }

    /**
     * Get add on service count
     *
     * @return
     */
    public Long count() {
        return addOnServiceRepository.count();
    }
}
