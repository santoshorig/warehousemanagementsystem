package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientBank;

import java.util.List;
import java.util.Optional;

/**
 * ClientBank interface
 */
public interface ClientBankService {

    /**
     * Get client bank
     *
     * @param id
     * @return
     */
    Optional<ClientBank> get(Integer id);

    /**
     * Edit client bank
     *
     * @param clientBank
     * @return
     */
    ClientBank edit(ClientBank clientBank);

    /**
     * Delete client bank
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client bank basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientBank> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client bank
     *
     * @return
     */
    List<ClientBank> getAll();

    /**
     * Get client bank count
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
    List<ClientBank> getByClient(Client client);
}
