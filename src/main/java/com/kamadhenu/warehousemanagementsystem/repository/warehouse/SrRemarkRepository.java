package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sr Remark repository class
 */
@Repository
public interface SrRemarkRepository extends JpaRepository<SrRemark, Integer> {

    /**
     * Get sr remark basis of sr ordered by remark date descending
     *
     * @param sr
     * @return
     */
    List<SrRemark> findBySrOrderByIdDescRemarkDateDesc(Sr sr);

}
