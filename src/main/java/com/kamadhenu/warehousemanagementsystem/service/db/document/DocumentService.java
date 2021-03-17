package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Document interface
 */
public interface DocumentService {

    /**
     * Get document
     *
     * @param id
     * @return
     */
    Optional<Document> get(Integer id);

    /**
     * Edit document
     *
     * @param document
     * @return
     */
    Document edit(Document document);

    /**
     * Delete document
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Document> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all document
     *
     * @return
     */
    List<Document> getAll();

    /**
     * Get document count
     *
     * @return
     */
    Long count();

    /**
     * Store file
     *
     * @param multipartFile
     * @return
     */
    Document store(MultipartFile multipartFile) throws IOException;
}
