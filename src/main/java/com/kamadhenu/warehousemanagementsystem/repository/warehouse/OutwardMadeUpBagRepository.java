package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardMadeUpBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Outward Made Up Bag repository class
 */
@Repository
public interface OutwardMadeUpBagRepository extends JpaRepository<OutwardMadeUpBag, Integer> {

    /**
     * Find outward made up bag by outward
     *
     * @param outward
     * @return
     */
    List<OutwardMadeUpBag> findByOutward(Outward outward);
}
