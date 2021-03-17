package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;

import java.util.List;
import java.util.Optional;

/**
 * DocumentType interface
 */
public interface DocumentTypeService {

    /**
     * Get document type
     *
     * @param id
     * @return
     */
    Optional<DocumentType> get(Integer id);

    /**
     * Edit document type
     *
     * @param documentType
     * @return
     */
    DocumentType edit(DocumentType documentType);

    /**
     * Delete document type
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all document type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DocumentType> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all document type
     *
     * @return
     */
    List<DocumentType> getAll();

    /**
     * Get document type count
     *
     * @return
     */
    Long count();

    /**
     * Get document type
     *
     * @param documentPurpose
     * @return
     */
    List<DocumentType> getByDocumentPurpose(DocumentPurpose documentPurpose);

    /**
     * Get document type
     *
     * @param name
     * @return
     */
    Optional<DocumentType> getByName(String name);
}
