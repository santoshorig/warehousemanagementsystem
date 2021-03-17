package com.kamadhenu.warehousemanagementsystem.service.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractDocument;
import com.kamadhenu.warehousemanagementsystem.repository.client.ContractDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractDocumentServiceImpl implements ContractDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractDocumentServiceImpl.class);

    @Autowired
    private ContractDocumentRepository contractDocumentRepository;

    /**
     * Get contract document
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ContractDocument> get(Integer id) {
        return contractDocumentRepository.findById(id);
    }

    /**
     * Edit contract document
     *
     * @param contractDocument
     * @return
     */
    @Override
    public ContractDocument edit(ContractDocument contractDocument) {
        return contractDocumentRepository.save(contractDocument);
    }

    /**
     * Delete contract document
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        contractDocumentRepository.deleteById(id);
    }

    /**
     * Get all contract document basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ContractDocument> getAll(Integer pageNumber, Integer pageSize) {
        return contractDocumentRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all contract document
     *
     * @return
     */
    @Override
    public List<ContractDocument> getAll() {
        return contractDocumentRepository.findAll();
    }

    /**
     * Get contract document count
     *
     * @return
     */
    public Long count() {
        return contractDocumentRepository.count();
    }

    /**
     * Get all contract document
     *
     * @param contract
     * @return
     */
    @Override
    public List<ContractDocument> getByContract(Contract contract) {
        return contractDocumentRepository.findByContract(contract);
    }
}
