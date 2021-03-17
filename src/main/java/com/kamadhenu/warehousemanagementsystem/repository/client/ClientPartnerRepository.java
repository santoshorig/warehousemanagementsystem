package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * client Partner repository class
 */
@Repository
public interface ClientPartnerRepository extends JpaRepository<ClientPartner, Integer> {

    /**
     * Get client partner
     *
     * @param client
     * @return
     */
    List<ClientPartner> findByClient(Client client);

}
