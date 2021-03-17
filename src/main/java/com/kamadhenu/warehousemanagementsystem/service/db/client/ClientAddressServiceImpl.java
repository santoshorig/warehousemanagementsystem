package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientAddressServiceImpl implements ClientAddressService {

    @Autowired
    private ClientAddressRepository clientAddressRepository;

    /**
     * Get client address
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientAddress> get(Integer id) {
        return clientAddressRepository.findById(id);
    }

    /**
     * Edit client address
     *
     * @param clientAddress
     * @return
     */
    @Override
    public ClientAddress edit(ClientAddress clientAddress) {
        return clientAddressRepository.save(clientAddress);
    }

    /**
     * Delete client address
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientAddressRepository.deleteById(id);
    }

    /**
     * Get all client address basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientAddress> getAll(Integer pageNumber, Integer pageSize) {
        return clientAddressRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client address
     *
     * @return
     */
    @Override
    public List<ClientAddress> getAll() {
        return clientAddressRepository.findAll();
    }

    /**
     * Get client address count
     *
     * @return
     */
    public Long count() {
        return clientAddressRepository.count();
    }

    /**
     * Get client address
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientAddress> getByClient(Client client) {
        return clientAddressRepository.findByClient(client);
    }

    /**
     * Get client address
     *
     * @param client
     * @param addressType
     * @return
     */
    @Override
    public Optional<ClientAddress> getByClientAndAddressType(Client client, String addressType) {
        return clientAddressRepository.findByClientAndAddressType(client, addressType);
    }
}
