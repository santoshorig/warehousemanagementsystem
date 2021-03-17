package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * client repository class
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Get client by status
     *
     * @param statusList
     * @param businessType
     * @return
     */
    List<Client> findByStatusInAndBusinessTypeIn(List<String> statusList, List<BusinessType> businessType);

    /**
     * Get client by code
     *
     * @param code
     * @return
     */
    Optional<Client> findByCode(String code);

    /**
     * Get client count by location
     *
     * @param addressType
     * @return
     */
    @Query("SELECT l.id AS location, COUNT(c.id) AS count FROM Client c LEFT JOIN ClientAddress ca ON ca.client.id = c.id"
            + " LEFT JOIN Location l ON l.id = ca.location.id WHERE ca.addressType = ?1 GROUP BY l.id")
    List<ClientLocationCount> countClientByLocation(String addressType);

    /**
     * Get client count by status
     *
     * @return
     */
    @Query("SELECT c.status AS status, COUNT(c.id) AS count FROM Client c GROUP BY c.status")
    List<ClientStatusCount> countClientByStatus();
}
