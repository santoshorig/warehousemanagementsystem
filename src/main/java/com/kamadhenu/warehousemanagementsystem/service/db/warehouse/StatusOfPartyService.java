package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.StatusOfParty;

import java.util.List;
import java.util.Optional;

/**
 * Status Of Party interface
 */
public interface StatusOfPartyService {

    /**
     * Get status of party
     *
     * @param id
     * @return
     */
    Optional<StatusOfParty> get(Integer id);

    /**
     * Edit status of party
     *
     * @param statusOfParty
     * @return
     */
    StatusOfParty edit(StatusOfParty statusOfParty);

    /**
     * Delete status of party
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all status of party basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<StatusOfParty> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all status of party
     *
     * @return
     */
    List<StatusOfParty> getAll();

    /**
     * Get status of party count
     *
     * @return
     */
    Long count();
}
