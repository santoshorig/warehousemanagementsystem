package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientConstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * client Constitution repository class
 */
@Repository
public interface ClientConstitutionRepository extends JpaRepository<ClientConstitution, Integer> {

}
