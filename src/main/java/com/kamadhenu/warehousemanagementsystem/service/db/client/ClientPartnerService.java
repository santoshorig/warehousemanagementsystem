package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;

import java.util.List;
import java.util.Optional;

/**
 * ClientPartner interface
 */
public interface ClientPartnerService {

    /**
     * Get client partner
     *
     * @param id
     * @return
     */
    Optional<ClientPartner> get(Integer id);

    /**
     * Edit client partner
     *
     * @param clientPartner
     * @return
     */
    ClientPartner edit(ClientPartner clientPartner);

    /**
     * Delete client partner
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client partner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientPartner> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client partner
     *
     * @return
     */
    List<ClientPartner> getAll();

    /**
     * Get client partner count
     *
     * @return
     */
    Long count();

    /**
     * Get client partner
     *
     * @param client
     * @return
     */
    List<ClientPartner> getByClient(Client client);
}
