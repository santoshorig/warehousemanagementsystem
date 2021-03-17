package com.kamadhenu.warehousemanagementsystem.repository.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Bag Type repository class
 */
@Repository
public interface BagTypeRepository extends JpaRepository<BagType, Integer> {

}
