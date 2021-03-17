package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.HeadOffice;

import java.util.List;
import java.util.Optional;

/**
 * Head Office interface
 */
public interface HeadOfficeService {

    /**
     * Get head office
     *
     * @param id
     * @return
     */
    Optional<HeadOffice> get(Integer id);

    /**
     * Edit head office
     *
     * @param headOffice
     * @return
     */
    HeadOffice edit(HeadOffice headOffice);

    /**
     * Delete head office
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all head office basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<HeadOffice> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all head office
     *
     * @return
     */
    List<HeadOffice> getAll();

    /**
     * Get head office count
     *
     * @return
     */
    Long count();
}
