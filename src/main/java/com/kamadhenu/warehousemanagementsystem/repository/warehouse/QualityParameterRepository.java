package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Quality Parameter repository class
 */
@Repository
public interface QualityParameterRepository extends JpaRepository<QualityParameter, Integer> {

}
