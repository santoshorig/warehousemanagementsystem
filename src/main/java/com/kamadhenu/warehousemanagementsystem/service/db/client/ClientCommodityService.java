package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientCommodity;

import java.util.List;
import java.util.Optional;

/**
 * ClientCommodity interface
 */
public interface ClientCommodityService {

    /**
     * Get client commodity
     *
     * @param id
     * @return
     */
    Optional<ClientCommodity> get(Integer id);

    /**
     * Edit client commodity
     *
     * @param clientCommodity
     * @return
     */
    ClientCommodity edit(ClientCommodity clientCommodity);

    /**
     * Delete client commodity
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientCommodity> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client commodity
     *
     * @return
     */
    List<ClientCommodity> getAll();

    /**
     * Get client commodity count
     *
     * @return
     */
    Long count();

    /**
     * Get all client commodity by client
     *
     * @return
     */
    List<ClientCommodity> getByClient(Client client);
}
