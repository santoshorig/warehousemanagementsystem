package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WeighbridgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Weighbridge Type repository class
 */
@Repository
public interface WeighbridgeTypeRepository extends JpaRepository<WeighbridgeType, Integer> {

}
