package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward Remark repository class
 */
@Repository
public interface OutwardRemarkRepository extends JpaRepository<OutwardRemark, Integer> {

    /**
     * Get outward remark basis of outward ordered by remark date descending
     *
     * @param outward
     * @return
     */
    List<OutwardRemark> findByOutwardOrderByIdDescRemarkDateDesc(Outward outward);

}
