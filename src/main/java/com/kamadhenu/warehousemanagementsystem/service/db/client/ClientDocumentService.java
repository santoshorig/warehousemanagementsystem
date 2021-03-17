package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;

import java.util.List;
import java.util.Optional;

/**
 * ClientDocument interface
 */
public interface ClientDocumentService {

    /**
     * Get client document
     *
     * @param id
     * @return
     */
    Optional<ClientDocument> get(Integer id);

    /**
     * Edit client document
     *
     * @param clientDocument
     * @return
     */
    ClientDocument edit(ClientDocument clientDocument);

    /**
     * Delete client document
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientDocument> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client document
     *
     * @return
     */
    List<ClientDocument> getAll();

    /**
     * Get client document count
     *
     * @return
     */
    Long count();

    /**
     * Get all client document
     *
     * @return
     */
    List<ClientDocument> getByClient(Client client);
}
