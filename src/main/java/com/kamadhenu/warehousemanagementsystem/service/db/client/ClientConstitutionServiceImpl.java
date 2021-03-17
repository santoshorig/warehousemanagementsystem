package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientConstitution;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientConstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientConstitutionServiceImpl implements ClientConstitutionService {

    @Autowired
    private ClientConstitutionRepository clientConstitutionRepository;

    /**
     * Get client constitution
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientConstitution> get(Integer id) {
        return clientConstitutionRepository.findById(id);
    }

    /**
     * Edit client constitution
     *
     * @param clientConstitution
     * @return
     */
    @Override
    public ClientConstitution edit(ClientConstitution clientConstitution) {
        return clientConstitutionRepository.save(clientConstitution);
    }

    /**
     * Delete client constitution
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientConstitutionRepository.deleteById(id);
    }

    /**
     * Get all client constitution basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientConstitution> getAll(Integer pageNumber, Integer pageSize) {
        return clientConstitutionRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client constitution
     *
     * @return
     */
    @Override
    public List<ClientConstitution> getAll() {
        return clientConstitutionRepository.findAll();
    }

    /**
     * Get client constitution count
     *
     * @return
     */
    public Long count() {
        return clientConstitutionRepository.count();
    }
}
