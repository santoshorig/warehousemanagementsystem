package com.kamadhenu.warehousemanagementsystem.repository.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Utilization Factor repository class
 */
@Repository
public interface UtilizationFactorRepository extends JpaRepository<UtilizationFactor, Integer> {

    /**
     * Find by commodity and business type
     *
     * @param commodity
     * @param businessType
     * @return
     */
    List<UtilizationFactor> findByCommodityAndBusinessType(Commodity commodity, BusinessType businessType);
}
