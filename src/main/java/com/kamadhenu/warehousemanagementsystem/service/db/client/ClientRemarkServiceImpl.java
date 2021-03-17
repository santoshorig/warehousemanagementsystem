package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientRemark;
import com.kamadhenu.warehousemanagementsystem.repository.client.ClientRemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientRemarkServiceImpl implements ClientRemarkService {

    @Autowired
    private ClientRemarkRepository clientRemarkRepository;

    /**
     * Get client remark
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ClientRemark> get(Integer id) {
        return clientRemarkRepository.findById(id);
    }

    /**
     * Edit client remark
     *
     * @param ClientRemark
     * @return
     */
    @Override
    public ClientRemark edit(ClientRemark ClientRemark) {
        return clientRemarkRepository.save(ClientRemark);
    }

    /**
     * Delete client remark
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        clientRemarkRepository.deleteById(id);
    }

    /**
     * Get all client remark basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ClientRemark> getAll(Integer pageNumber, Integer pageSize) {
        return clientRemarkRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all client remark
     *
     * @return
     */
    @Override
    public List<ClientRemark> getAll() {
        return clientRemarkRepository.findAll();
    }

    /**
     * Get client remark count
     *
     * @return
     */
    public Long count() {
        return clientRemarkRepository.count();
    }

    /**
     * Get client remark
     *
     * @param client
     * @return
     */
    @Override
    public List<ClientRemark> getByClient(Client client) {
        return clientRemarkRepository.findByClientOrderByIdDescRemarkDateDesc(client);
    }
}
