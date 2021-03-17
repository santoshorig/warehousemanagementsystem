package com.kamadhenu.warehousemanagementsystem.repository.risk;

import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Highmark Risk Category repository class
 */
@Repository
public interface HighmarkRiskCategoryRepository extends JpaRepository<HighmarkRiskCategory, Integer> {

}
