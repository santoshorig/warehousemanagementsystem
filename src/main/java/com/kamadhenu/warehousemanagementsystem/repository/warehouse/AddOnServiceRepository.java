package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add On Service repository class
 */
@Repository
public interface AddOnServiceRepository extends JpaRepository<AddOnService, Integer> {

}
