package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * client bank repository class
 */
@Repository
public interface ClientBankRepository extends JpaRepository<ClientBank, Integer> {

    /**
     * Get client Bank
     *
     * @param client
     * @return
     */
    List<ClientBank> findByClient(Client client);

}
