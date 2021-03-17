package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractDocument;

import java.util.List;
import java.util.Optional;

/**
 * ContractDocument interface
 */
public interface ContractDocumentService {

    /**
     * Get contract document
     *
     * @param id
     * @return
     */
    Optional<ContractDocument> get(Integer id);

    /**
     * Edit contract document
     *
     * @param contractDocument
     * @return
     */
    ContractDocument edit(ContractDocument contractDocument);

    /**
     * Delete contract document
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all contract document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ContractDocument> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all contract document
     *
     * @return
     */
    List<ContractDocument> getAll();

    /**
     * Get contract document count
     *
     * @return
     */
    Long count();

    /**
     * Get all contract document
     *
     * @return
     */
    List<ContractDocument> getByContract(Contract contract);
}
