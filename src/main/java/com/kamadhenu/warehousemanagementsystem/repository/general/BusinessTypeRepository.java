package com.kamadhenu.warehousemanagementsystem.repository.general;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Business Type repository class
 */
@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Integer> {

}
