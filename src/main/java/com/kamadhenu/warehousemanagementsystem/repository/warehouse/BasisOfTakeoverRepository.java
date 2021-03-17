package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Basis Of Takeover repository class
 */
@Repository
public interface BasisOfTakeoverRepository extends JpaRepository<BasisOfTakeover, Integer> {

}
