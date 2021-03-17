package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientType;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientTypeServiceImpl implements ClientTypeService {

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    /**
     * Get client type
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientType> get(Integer id) {
        return clientTypeRepository.findById(id);
    }

    /**
     * Edit client type
     *
     * @param clientType
     * @return
     */
    @Override
    public ClientType edit(ClientType clientType) {
        return clientTypeRepository.save(clientType);
    }

    /**
     * Delete client type
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientTypeRepository.deleteById(id);
    }

    /**
     * Get all client type basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientType> getAll(Integer pageNumber, Integer pageSize) {
        return clientTypeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client type
     *
     * @return
     */
    @Override
    public List<ClientType> getAll() {
        return clientTypeRepository.findAll();
    }

    /**
     * Get client type count
     *
     * @return
     */
    public Long count() {
        return clientTypeRepository.count();
    }
}
