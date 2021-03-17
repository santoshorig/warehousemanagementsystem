package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;

import java.util.List;
import java.util.Optional;

/**
 * Add On Service interface
 */
public interface AddOnServiceService {

    /**
     * Get add on service
     *
     * @param id
     * @return
     */
    Optional<AddOnService> get(Integer id);

    /**
     * Edit add on service
     *
     * @param addOnService
     * @return
     */
    AddOnService edit(AddOnService addOnService);

    /**
     * Delete add on service
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all add on service basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<AddOnService> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all add on service
     *
     * @return
     */
    List<AddOnService> getAll();

    /**
     * Get add on service count
     *
     * @return
     */
    Long count();
}
