package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.repository.document.DocumentPurposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentPurposeServiceImpl implements DocumentPurposeService {

    @Autowired
    private DocumentPurposeRepository documentPurposeRepository;

    /**
     * Get document purpose
     *
     * @param id
     * @return
     */
    @Override
    public Optional<DocumentPurpose> get(Integer id) {
        return documentPurposeRepository.findById(id);
    }

    /**
     * Edit document purpose
     *
     * @param documentPurpose
     * @return
     */
    @Override
    public DocumentPurpose edit(DocumentPurpose documentPurpose) {
        return documentPurposeRepository.save(documentPurpose);
    }

    /**
     * Delete document purpose
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        documentPurposeRepository.deleteById(id);
    }

    /**
     * Get all document purpose basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<DocumentPurpose> getAll(Integer pageNumber, Integer pageSize) {
        return documentPurposeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all document purpose
     *
     * @return
     */
    @Override
    public List<DocumentPurpose> getAll() {
        return documentPurposeRepository.findAll();
    }

    /**
     * Get document purpose count
     *
     * @return
     */
    public Long count() {
        return documentPurposeRepository.count();
    }

    /**
     * Get document purpose
     *
     * @param name
     * @return
     */
    public Optional<DocumentPurpose> getByName(String name) {
        return documentPurposeRepository.findByName(name);
    }
}
