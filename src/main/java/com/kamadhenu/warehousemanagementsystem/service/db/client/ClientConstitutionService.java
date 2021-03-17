package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientConstitution;

import java.util.List;
import java.util.Optional;

/**
 * ClientConstitution interface
 */
public interface ClientConstitutionService {

    /**
     * Get client constitution
     *
     * @param id
     * @return
     */
    Optional<ClientConstitution> get(Integer id);

    /**
     * Edit client constitution
     *
     * @param clientConstitution
     * @return
     */
    ClientConstitution edit(ClientConstitution clientConstitution);

    /**
     * Delete client constitution
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all client constitution basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ClientConstitution> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all client constitution
     *
     * @return
     */
    List<ClientConstitution> getAll();

    /**
     * Get client constitution count
     *
     * @return
     */
    Long count();
}
