package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * client Signatory repository class
 */
@Repository
public interface ClientSignatoryRepository extends JpaRepository<ClientSignatory, Integer> {

    /**
     * Get client Signatory
     *
     * @param client
     * @return
     */
    List<ClientSignatory> findByClient(Client client);

}
