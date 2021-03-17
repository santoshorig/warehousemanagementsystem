package com.kamadhenu.warehousemanagementsystem.repository.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Document Type repository class
 */
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

    /**
     * Get document type
     *
     * @param documentPurpose
     * @return
     */
    List<DocumentType> findByDocumentPurpose(DocumentPurpose documentPurpose);

    /**
     * Get document type
     *
     * @param name
     * @return
     */
    Optional<DocumentType> findByName(String name);
}
