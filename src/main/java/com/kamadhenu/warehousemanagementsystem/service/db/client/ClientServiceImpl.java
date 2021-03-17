package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientStatusCount;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientRepository;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Get client
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Client> get(Integer id) {
        return clientRepository.findById(id);
    }

    /**
     * Edit client
     *
     * @param client
     * @return
     */
    @Override
    public Client edit(Client client) {
        return updateIfActive(client);

    }

    /**
     * Delete client
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }

    /**
     * Get all client basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Client> getAll(Integer pageNumber, Integer pageSize) {
        return clientRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client
     *
     * @return
     */
    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    /**
     * Get client count
     *
     * @return
     */
    public Long count() {
        return clientRepository.count();
    }

    /**
     * Get client by status
     *
     * @return
     */
    public List<Client> getByStatusAndBusinessType() {
        return clientRepository
                .findByStatusInAndBusinessTypeIn(
                        helperService.getClientStatusByRole(),
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get clients by provided status and business type
     *
     * @param statusList
     * @return
     */
    public List<Client> getByStatusAndBusinessType(List<String> statusList) {
        return clientRepository
                .findByStatusInAndBusinessTypeIn(
                        statusList,
                        helperService.getUserBusinessType()
                );
    }

    /**
     * Get client by code
     *
     * @param code
     * @return
     */
    public Optional<Client> getByCode(String code) {
        return clientRepository.findByCode(code);
    }

    /**
     * Check if client can be edited
     *
     * @param client
     * @return
     */
    public Boolean canEdit(Client client) {
      return client.getStatus().equalsIgnoreCase(constantService.getCLIENT_STATUS().get("pendingforbusinessreview")) || 
          (client.getId() == null);
    }

    /**
     * Update status to under progress if client is active
     *
     * @param client
     * @return
     */
    public Client updateIfActive(Client client) {
        if (client.getId() != null) {
            Optional<Client> existingClient = get(client.getId());
            if (existingClient.isPresent()) {
                Client existingClientModel = existingClient.get();
                if (existingClientModel.getStatus()
                        .equalsIgnoreCase(constantService.getCLIENT_STATUS().get("approved"))) {
                    client.setStatus(constantService.getCLIENT_STATUS().get("underprocess"));
                }
                if (existingClientModel.getCommodity().size() > 0) {
                    client.getCommodity().addAll(existingClientModel.getCommodity());
                }
            }
        }
        return clientRepository.save(client);
    }

    /**
     * Get active clients by business type
     *
     * @return
     */
    public List<Client> getActiveByBusinessType() {
        List<String> statusList = new ArrayList<>();
        statusList.add(constantService.getCLIENT_STATUS().get("approved"));
        return getByStatusAndBusinessType(statusList);
    }

    /**
     * Get client count by location
     *
     * @param addressType
     * @return
     */
    public List<ClientLocationCount> getCountClientByLocation(String addressType) {
        return clientRepository.countClientByLocation(addressType);
    }

    /**
     * Get client count by status
     *
     * @return
     */
    public List<ClientStatusCount> getCountClientByStatus() {
        return clientRepository.countClientByStatus();
    }
}
