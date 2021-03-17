package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardMadeUpBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inward Made Up Bag repository class
 */
@Repository
public interface InwardMadeUpBagRepository extends JpaRepository<InwardMadeUpBag, Integer> {

    /**
     * Find inward made up bag by inward
     *
     * @param inward
     * @return
     */
    List<InwardMadeUpBag> findByInward(Inward inward);

    /**
     * Find inward made up bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    List<InwardMadeUpBag> findByWarehouseStack(WarehouseStack warehouseStack);

    /**
     * Find inward made up bag by warehouse stack
     *
     * @param inward
     * @param outward
     * @return
     */
    List<InwardMadeUpBag> findByInwardAndOutward(Inward inward, Boolean outward);
}
