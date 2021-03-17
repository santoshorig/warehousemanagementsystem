package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.repository.document.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    /**
     * Get document type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<DocumentType> get(Integer id) {
        return documentTypeRepository.findById(id);
    }

    /**
     * Edit document type
     *
     * @param documentType
     * @return
     */
    @Override
    public DocumentType edit(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    /**
     * Delete document type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        documentTypeRepository.deleteById(id);
    }

    /**
     * Get all document type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<DocumentType> getAll(Integer pageNumber, Integer pageSize) {
        return documentTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all document type
     *
     * @return
     */
    @Override
    public List<DocumentType> getAll() {
        return documentTypeRepository.findAll();
    }

    /**
     * Get document type count
     *
     * @return
     */
    public Long count() {
        return documentTypeRepository.count();
    }

    /**
     * Get all document type
     *
     * @param documentPurpose
     * @return
     */
    public List<DocumentType> getByDocumentPurpose(DocumentPurpose documentPurpose) {
        return documentTypeRepository.findByDocumentPurpose(documentPurpose);
    }

    /**
     * Get document type
     *
     * @param name
     * @return
     */
    @Override
    public Optional<DocumentType> getByName(String name) {
        return documentTypeRepository.findByName(name);
    }
}
