package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward Remark repository class
 */
@Repository
public interface InwardRemarkRepository extends JpaRepository<InwardRemark, Integer> {

    /**
     * Get inward remark basis of inward ordered by remark date descending
     *
     * @param inward
     * @return
     */
    List<InwardRemark> findByInwardOrderByIdDescRemarkDateDesc(Inward inward);

}
