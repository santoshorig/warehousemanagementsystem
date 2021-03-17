package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * client Document repository class
 */
@Repository
public interface ClientDocumentRepository extends JpaRepository<ClientDocument, Integer> {

    /**
     * Get all client document
     *
     * @param client
     * @return
     */
    List<ClientDocument> findByClient(Client client);

    /**
     * Get all client document
     *
     * @param client
     * @return
     */
    List<ClientDocument> findByClientAndDocumentType(Client client, DocumentType documentType);

}
