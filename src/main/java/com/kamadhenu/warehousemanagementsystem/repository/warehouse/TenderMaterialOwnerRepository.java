package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderMaterialOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tender Material Owner repository class
 */
@Repository
public interface TenderMaterialOwnerRepository extends JpaRepository<TenderMaterialOwner, Integer> {

}
