package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;

import java.util.List;
import java.util.Optional;

/**
 * DocumentPurpose interface
 */
public interface DocumentPurposeService {

    /**
     * Get document purpose
     *
     * @param id
     * @return
     */
    Optional<DocumentPurpose> get(Integer id);

    /**
     * Edit document purpose
     *
     * @param documentPurpose
     * @return
     */
    DocumentPurpose edit(DocumentPurpose documentPurpose);

    /**
     * Delete document purpose
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all document purpose basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DocumentPurpose> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all document purpose
     *
     * @return
     */
    List<DocumentPurpose> getAll();

    /**
     * Get document purpose count
     *
     * @return
     */
    Long count();

    /**
     * Get document purpose
     *
     * @param name
     * @return
     */
    Optional<DocumentPurpose> getByName(String name);
}
