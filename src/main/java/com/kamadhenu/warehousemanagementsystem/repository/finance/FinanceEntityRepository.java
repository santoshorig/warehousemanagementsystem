package com.kamadhenu.warehousemanagementsystem.repository.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Finance Entity repository class
 */
@Repository
public interface FinanceEntityRepository extends JpaRepository<FinanceEntity, Integer> {

}
