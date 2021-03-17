package com.kamadhenu.warehousemanagementsystem.repository.finance;

import com.kamadhenu.warehousemanagementsystem.model.db.finance.Margin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Margin repository class
 */
@Repository
public interface MarginRepository extends JpaRepository<Margin, Integer> {

}
