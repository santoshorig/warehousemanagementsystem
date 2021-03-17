package com.kamadhenu.warehousemanagementsystem.repository.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Commodity repository class
 */
@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Integer> {

}
