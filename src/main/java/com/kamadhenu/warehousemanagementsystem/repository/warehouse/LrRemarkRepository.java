package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Lr Remark repository class
 */
@Repository
public interface LrRemarkRepository extends JpaRepository<LrRemark, Integer> {

    /**
     * Get lr remark basis of lr ordered by remark date descending
     *
     * @param lr
     * @return
     */
    List<LrRemark> findByLrOrderByIdDescRemarkDateDesc(Lr lr);

}
