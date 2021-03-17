package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;

import java.util.List;
import java.util.Optional;

/**
 * ClientAddress interface
 */
public interface ClientAddressService {

    /**
     * Get client address
     *
     * @param id
     * @return
     */
    Optional<ClientAddress> get(Integer id);

    /**
     * Edit client address
     *
     * @param clientAddress
     * @return
     */
    ClientAddress edit(ClientAddress clientAddress);

    /**
     * Delete client address
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client address basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientAddress> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client address
     *
     * @return
     */
    List<ClientAddress> getAll();

    /**
     * Get client address count
     *
     * @return
     */
    Long count();

    /**
     * Get client address
     *
     * @param client
     * @return
     */
    List<ClientAddress> getByClient(Client client);

    /**
     * Get client address
     *
     * @param client
     * @param addressType
     * @return
     */
    Optional<ClientAddress> getByClientAndAddressType(Client client, String addressType);
}
