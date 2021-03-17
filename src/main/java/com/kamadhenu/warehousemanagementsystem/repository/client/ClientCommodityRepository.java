package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Client Commodity repository class
 */
@Repository
public interface ClientCommodityRepository extends JpaRepository<ClientCommodity, Integer> {

    /**
     * Get client commodity
     *
     * @param client
     * @return
     */
    List<ClientCommodity> findByClient(Client client);

}
