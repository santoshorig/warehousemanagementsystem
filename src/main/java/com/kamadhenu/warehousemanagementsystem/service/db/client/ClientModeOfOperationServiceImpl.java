package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientModeOfOperation;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientModeOfOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientModeOfOperationServiceImpl implements ClientModeOfOperationService {

    @Autowired
    private ClientModeOfOperationRepository clientModeOfOperationRepository;

    /**
     * Get client mode of operation
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientModeOfOperation> get(Integer id) {
        return clientModeOfOperationRepository.findById(id);
    }

    /**
     * Edit client mode of operation
     *
     * @param clientModeOfOperation
     * @return
     */
    @Override
    public ClientModeOfOperation edit(ClientModeOfOperation clientModeOfOperation) {
        return clientModeOfOperationRepository.save(clientModeOfOperation);
    }

    /**
     * Delete client mode of operation
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientModeOfOperationRepository.deleteById(id);
    }

    /**
     * Get all client mode of operation basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientModeOfOperation> getAll(Integer pageNumber, Integer pageSize) {
        return clientModeOfOperationRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client mode of operation
     *
     * @return
     */
    @Override
    public List<ClientModeOfOperation> getAll() {
        return clientModeOfOperationRepository.findAll();
    }

    /**
     * Get client mode of operation count
     *
     * @return
     */
    public Long count() {
        return clientModeOfOperationRepository.count();
    }
}
