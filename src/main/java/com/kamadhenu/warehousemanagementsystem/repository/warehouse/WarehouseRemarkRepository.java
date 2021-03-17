package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Warehouse Remark repository class
 */
@Repository
public interface WarehouseRemarkRepository extends JpaRepository<WarehouseRemark, Integer> {

    /**
     * Get warehouse remark basis of warehouse ordered by remark date descending
     *
     * @param warehouse
     * @return
     */
    List<WarehouseRemark> findByWarehouseOrderByIdDescRemarkDateDesc(Warehouse warehouse);

}
