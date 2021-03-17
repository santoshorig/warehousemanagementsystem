package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Warehouse Type repository class
 */
@Repository
public interface WarehouseTypeRepository extends JpaRepository<WarehouseType, Integer> {

}
