package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientStatusCount;

import java.util.List;
import java.util.Optional;

/**
 * client interface
 */
public interface ClientService {

    /**
     * Get client
     *
     * @param id
     * @return
     */
    Optional<Client> get(Integer id);

    /**
     * Edit client
     *
     * @param client
     * @return
     */
    Client edit(Client client);

    /**
     * Delete client
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Client> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client
     *
     * @return
     */
    List<Client> getAll();

    /**
     * Get client count
     *
     * @return
     */
    Long count();

    /**
     * Get clients by status and business type
     *
     * @return
     */
    List<Client> getByStatusAndBusinessType();

    /**
     * Get clients by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Client> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get client by code
     *
     * @param code
     * @return
     */
    Optional<Client> getByCode(String code);

    /**
     * Check if client can be edited
     *
     * @param client
     * @return
     */
    Boolean canEdit(Client client);

    /**
     * Update status to under progress if client is active
     *
     * @param client
     * @return
     */
    Client updateIfActive(Client client);

    /**
     * Get active clients by business type
     *
     * @return
     */
    List<Client> getActiveByBusinessType();

    /**
     * Get client count by location
     *
     * @param addressType
     * @return
     */
    List<ClientLocationCount> getCountClientByLocation(String addressType);

    /**
     * Get client count by status
     *
     * @return
     */
    List<ClientStatusCount> getCountClientByStatus();
}
