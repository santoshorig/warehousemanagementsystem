package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientSignatoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientSignatoryServiceImpl implements ClientSignatoryService {

    @Autowired
    private ClientSignatoryRepository clientSignatoryRepository;

    /**
     * Get client signatory
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientSignatory> get(Integer id) {
        return clientSignatoryRepository.findById(id);
    }

    /**
     * Edit client signatory
     *
     * @param clientSignatory
     * @return
     */
    @Override
    public ClientSignatory edit(ClientSignatory clientSignatory) {
        return clientSignatoryRepository.save(clientSignatory);
    }

    /**
     * Delete client signatory
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientSignatoryRepository.deleteById(id);
    }

    /**
     * Get all client signatory basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientSignatory> getAll(Integer pageNumber, Integer pageSize) {
        return clientSignatoryRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client signatory
     *
     * @return
     */
    @Override
    public List<ClientSignatory> getAll() {
        return clientSignatoryRepository.findAll();
    }

    /**
     * Get client signatory count
     *
     * @return
     */
    public Long count() {
        return clientSignatoryRepository.count();
    }

    /**
     * Get client bank
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientSignatory> getByClient(Client client) {
        return clientSignatoryRepository.findByClient(client);
    }
}
