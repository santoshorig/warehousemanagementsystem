package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientType;

import java.util.List;
import java.util.Optional;

/**
 * ClientType interface
 */
public interface ClientTypeService {

    /**
     * Get client type
     *
     * @param id
     * @return
     */
    Optional<ClientType> get(Integer id);

    /**
     * Edit client type
     *
     * @param clientType
     * @return
     */
    ClientType edit(ClientType clientType);

    /**
     * Delete client type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client type
     *
     * @return
     */
    List<ClientType> getAll();

    /**
     * Get client type count
     *
     * @return
     */
    Long count();
}
