package com.kamadhenu.warehousemanagementsystem.repository.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Insurance repository class
 */
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    /**
     * Find insurance with effective to before certain date
     *
     * @param effectiveTo
     * @return
     */
    List<Insurance> findAllByEffectiveToBeforeAndActiveTrue(Date effectiveTo);

}
