package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;

import java.util.List;
import java.util.Optional;

/**
 * ClientSignatory interface
 */
public interface ClientSignatoryService {

    /**
     * Get client signatory
     *
     * @param id
     * @return
     */
    Optional<ClientSignatory> get(Integer id);

    /**
     * Edit client signatory
     *
     * @param clientSignatory
     * @return
     */
    ClientSignatory edit(ClientSignatory clientSignatory);

    /**
     * Delete client signatory
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client signatory basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientSignatory> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client signatory
     *
     * @return
     */
    List<ClientSignatory> getAll();

    /**
     * Get client signatory count
     *
     * @return
     */
    Long count();

    /**
     * Get client bank
     *
     * @param client
     * @return
     */
    List<ClientSignatory> getByClient(Client client);
}
