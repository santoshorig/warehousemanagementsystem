package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientRemark;

import java.util.List;
import java.util.Optional;

/**
 * Client Remark interface
 */
public interface ClientRemarkService {
    /**
     * Get clientRemark
     *
     * @param id
     * @return
     */
    Optional<ClientRemark> get(Integer id);

    /**
     * Edit client
     *
     * @param clientRemark
     * @return
     */
    ClientRemark edit(ClientRemark clientRemark);

    /**
     * Delete client
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientRemark> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client remark
     *
     * @return
     */
    List<ClientRemark> getAll();

    /**
     * Get client remark count
     *
     * @return
     */
    Long count();

    /**
     * Get all client remark basis client
     *
     * @param client
     * @return
     */
    List<ClientRemark> getByClient(Client client);
}
