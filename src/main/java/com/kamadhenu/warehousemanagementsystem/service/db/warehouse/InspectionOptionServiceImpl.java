package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionOption;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InspectionOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectionOptionServiceImpl implements InspectionOptionService {

    @Autowired
    private InspectionOptionRepository inspectionOptionRepository;

    /**
     * Get inspection option
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InspectionOption> get(Integer id) {
        return inspectionOptionRepository.findById(id);
    }

    /**
     * Edit inspection option
     *
     * @param inspectionOption
     * @return
     */
    @Override
    public InspectionOption edit(InspectionOption inspectionOption) {
        return inspectionOptionRepository.save(inspectionOption);
    }

    /**
     * Delete inspection option
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inspectionOptionRepository.deleteById(id);
    }

    /**
     * Get all inspection option basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InspectionOption> getAll(Integer pageNumber, Integer pageSize) {
        return inspectionOptionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inspection option
     *
     * @return
     */
    @Override
    public List<InspectionOption> getAll() {
        return inspectionOptionRepository.findAll();
    }

    /**
     * Get inspection option count
     *
     * @return
     */
    public Long count() {
        return inspectionOptionRepository.count();
    }
}
