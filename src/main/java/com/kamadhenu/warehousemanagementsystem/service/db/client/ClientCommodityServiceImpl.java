package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientCommodity;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = {"clientCommodity"})
@Service
public class ClientCommodityServiceImpl implements ClientCommodityService {

    @Autowired
    private ClientCommodityRepository clientCommodityRepository;

    /**
     * Get client commodity
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientCommodity> get(Integer id) {
        return clientCommodityRepository.findById(id);
    }

    /**
     * Edit client commodity
     *
     * @param clientCommodity
     * @return
     */
    @Override
    @CachePut
    public ClientCommodity edit(ClientCommodity clientCommodity) {
        return clientCommodityRepository.save(clientCommodity);
    }

    /**
     * Delete client commodity
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientCommodityRepository.deleteById(id);
    }

    /**
     * Get all client commodity basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable
    public List<ClientCommodity> getAll(Integer pageNumber, Integer pageSize) {
        return clientCommodityRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client commodity
     *
     * @return
     */
    @Override
    @Cacheable
    public List<ClientCommodity> getAll() {
        return clientCommodityRepository.findAll();
    }

    /**
     * Get client commodity count
     *
     * @return
     */
    public Long count() {
        return clientCommodityRepository.count();
    }

    /**
     * Get all client commodity by client
     *
     * @return
     */
    public List<ClientCommodity> getByClient(Client client) {
        return clientCommodityRepository.findByClient(client);
    }
}
