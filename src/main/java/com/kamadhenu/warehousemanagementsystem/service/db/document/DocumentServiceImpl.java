package com.kamadhenu.warehousemanagementsystem.service.db.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.repository.document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Get document
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Document> get(Integer id) {
        return documentRepository.findById(id);
    }

    /**
     * Edit document
     *
     * @param document
     * @return
     */
    @Override
    public Document edit(Document document) {
        return documentRepository.save(document);
    }

    /**
     * Delete document
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    /**
     * Get all document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Document> getAll(Integer pageNumber, Integer pageSize) {
        return documentRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all document
     *
     * @return
     */
    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    /**
     * Get document count
     *
     * @return
     */
    public Long count() {
        return documentRepository.count();
    }

    /**
     * Store file
     *
     * @param multipartFile
     * @return
     */
    public Document store(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IOException("Filename contains invalid path sequence " + fileName);
        }
        Document document = new Document();
        document.setFileName(fileName);
        document.setFileType(multipartFile.getContentType());
        document.setFileSize(Math.toIntExact(multipartFile.getSize()));
        document.setFileContent(multipartFile.getBytes());
        return edit(document);
    }
}
