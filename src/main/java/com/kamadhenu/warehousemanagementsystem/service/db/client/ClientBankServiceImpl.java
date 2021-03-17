package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientBank;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientBankServiceImpl implements ClientBankService {

    @Autowired
    private ClientBankRepository clientBankRepository;

    /**
     * Get client bank
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientBank> get(Integer id) {
        return clientBankRepository.findById(id);
    }

    /**
     * Edit client bank
     *
     * @param clientBank
     * @return
     */
    @Override
    public ClientBank edit(ClientBank clientBank) {
        return clientBankRepository.save(clientBank);
    }

    /**
     * Delete client bank
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientBankRepository.deleteById(id);
    }

    /**
     * Get all client bank basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientBank> getAll(Integer pageNumber, Integer pageSize) {
        return clientBankRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client bank
     *
     * @return
     */
    @Override
    public List<ClientBank> getAll() {
        return clientBankRepository.findAll();
    }

    /**
     * Get client bank count
     *
     * @return
     */
    public Long count() {
        return clientBankRepository.count();
    }

    /**
     * Get client bank
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientBank> getByClient(Client client) {
        return clientBankRepository.findByClient(client);
    }
}
