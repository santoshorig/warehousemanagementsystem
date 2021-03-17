package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Consumable repository class
 */
@Repository
public interface ConsumableRepository extends JpaRepository<Consumable, Integer> {

}
