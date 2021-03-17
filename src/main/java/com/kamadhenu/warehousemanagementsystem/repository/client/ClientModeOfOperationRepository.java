package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientModeOfOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * client Mode Of Operation repository class
 */
@Repository
public interface ClientModeOfOperationRepository extends JpaRepository<ClientModeOfOperation, Integer> {

}
