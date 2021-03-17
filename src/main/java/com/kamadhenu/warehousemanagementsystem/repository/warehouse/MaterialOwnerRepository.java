package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.MaterialOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Material Owner repository class
 */
@Repository
public interface MaterialOwnerRepository extends JpaRepository<MaterialOwner, Integer> {

}
