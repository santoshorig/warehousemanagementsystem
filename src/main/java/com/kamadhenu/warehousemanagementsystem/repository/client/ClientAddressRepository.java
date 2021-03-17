package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * client Address repository class
 */
@Repository
public interface ClientAddressRepository extends JpaRepository<ClientAddress, Integer> {

    /**
     * Get client address
     *
     * @param client
     * @return
     */
    List<ClientAddress> findByClient(Client client);

    /**
     * Get client address by client and address type
     *
     * @param client
     * @param addressType
     * @return
     */
    Optional<ClientAddress> findByClientAndAddressType(Client client, String addressType);

}
