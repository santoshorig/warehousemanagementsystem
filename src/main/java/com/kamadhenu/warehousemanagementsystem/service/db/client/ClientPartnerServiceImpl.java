package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientPartnerServiceImpl implements ClientPartnerService {

    @Autowired
    private ClientPartnerRepository clientPartnerRepository;

    /**
     * Get client partner
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientPartner> get(Integer id) {
        return clientPartnerRepository.findById(id);
    }

    /**
     * Edit client partner
     *
     * @param clientPartner
     * @return
     */
    @Override
    public ClientPartner edit(ClientPartner clientPartner) {
        return clientPartnerRepository.save(clientPartner);
    }

    /**
     * Delete client partner
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientPartnerRepository.deleteById(id);
    }

    /**
     * Get all client partner basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientPartner> getAll(Integer pageNumber, Integer pageSize) {
        return clientPartnerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client partner
     *
     * @return
     */
    @Override
    public List<ClientPartner> getAll() {
        return clientPartnerRepository.findAll();
    }

    /**
     * Get client partner count
     *
     * @return
     */
    public Long count() {
        return clientPartnerRepository.count();
    }

    /**
     * Get client partner
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientPartner> getByClient(Client client) {
        return clientPartnerRepository.findByClient(client);
    }
}
