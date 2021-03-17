package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientDocumentServiceImpl implements ClientDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDocumentServiceImpl.class);

    @Autowired
    private ClientDocumentRepository clientDocumentRepository;

    /**
     * Get client document
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientDocument> get(Integer id) {
        return clientDocumentRepository.findById(id);
    }

    /**
     * Edit client document
     *
     * @param clientDocument
     * @return
     */
    @Override
    public ClientDocument edit(ClientDocument clientDocument) {
        return clientDocumentRepository.save(clientDocument);
    }

    /**
     * Delete client document
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientDocumentRepository.deleteById(id);
    }

    /**
     * Get all client document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientDocument> getAll(Integer pageNumber, Integer pageSize) {
        return clientDocumentRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client document
     *
     * @return
     */
    @Override
    public List<ClientDocument> getAll() {
        return clientDocumentRepository.findAll();
    }

    /**
     * Get client document count
     *
     * @return
     */
    public Long count() {
        return clientDocumentRepository.count();
    }

    /**
     * Get all client document
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientDocument> getByClient(Client client) {
        return clientDocumentRepository.findByClient(client);
    }
}
