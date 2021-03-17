package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract Document repository class
 */
@Repository
public interface ContractDocumentRepository extends JpaRepository<ContractDocument, Integer> {

    /**
     * Get all contract document
     *
     * @param contract
     * @return
     */
    List<ContractDocument> findByContract(Contract contract);

    /**
     * Get all contract document
     *
     * @param contract
     * @return
     */
    List<ContractDocument> findByContractAndDocumentType(Contract contract, DocumentType documentType);

}
