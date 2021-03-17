package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosureRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Closure Remark repository class
 */
@Repository
public interface WarehouseClosureRemarkRepository extends JpaRepository<WarehouseClosureRemark, Integer> {

    /**
     * Find warehouse closure remark by warehouse remark
     *
     * @param warehouseClosure
     * @return
     */
    List<WarehouseClosureRemark> findByWarehouseClosure(WarehouseClosure warehouseClosure);
}
