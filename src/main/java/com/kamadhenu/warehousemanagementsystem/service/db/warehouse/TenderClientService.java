package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderClient;

import java.util.List;
import java.util.Optional;

/**
 * Tender Client interface
 */
public interface TenderClientService {

    /**
     * Get tender client
     *
     * @param id
     * @return
     */
    Optional<TenderClient> get(Integer id);

    /**
     * Edit tender client
     *
     * @param tenderClient
     * @return
     */
    TenderClient edit(TenderClient tenderClient);

    /**
     * Delete tender client
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all tender client basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TenderClient> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all tender client
     *
     * @return
     */
    List<TenderClient> getAll();

    /**
     * Get tender client count
     *
     * @return
     */
    Long count();
}
