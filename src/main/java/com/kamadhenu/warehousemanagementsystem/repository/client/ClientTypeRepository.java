package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * client Type repository class
 */
@Repository
public interface ClientTypeRepository extends JpaRepository<ClientType, Integer> {

}
