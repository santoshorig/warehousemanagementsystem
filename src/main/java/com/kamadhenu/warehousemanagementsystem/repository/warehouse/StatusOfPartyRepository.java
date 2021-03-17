package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.StatusOfParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Status Of Party repository class
 */
@Repository
public interface StatusOfPartyRepository extends JpaRepository<StatusOfParty, Integer> {

}
