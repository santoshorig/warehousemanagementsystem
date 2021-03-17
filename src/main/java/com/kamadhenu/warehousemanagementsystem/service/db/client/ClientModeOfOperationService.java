package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientModeOfOperation;

import java.util.List;
import java.util.Optional;

/**
 * ClientModeOfOperation interface
 */
public interface ClientModeOfOperationService {

    /**
     * Get client mode of operation
     *
     * @param id
     * @return
     */
    Optional<ClientModeOfOperation> get(Integer id);

    /**
     * Edit client mode of operation
     *
     * @param clientModeOfOperation
     * @return
     */
    ClientModeOfOperation edit(ClientModeOfOperation clientModeOfOperation);

    /**
     * Delete client mode of operation
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client mode of operation basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientModeOfOperation> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client mode of operation
     *
     * @return
     */
    List<ClientModeOfOperation> getAll();

    /**
     * Get client mode of operation count
     *
     * @return
     */
    Long count();
}
