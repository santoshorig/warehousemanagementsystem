package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Do Remark repository class
 */
@Repository
public interface DoRemarkRepository extends JpaRepository<DoRemark, Integer> {

    /**
     * Get do remark basis of do ordered by remark date descending
     *
     * @param doModel
     * @return
     */
    List<DoRemark> findByDoModelOrderByIdDescRemarkDateDesc(Do doModel);

}
