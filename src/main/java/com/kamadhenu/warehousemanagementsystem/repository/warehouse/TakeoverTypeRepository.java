package com.kamadhenu.warehousemanagementsystem.repository.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TakeoverType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Takeover Type repository class
 */
@Repository
public interface TakeoverTypeRepository extends JpaRepository<TakeoverType, Integer> {

    /**
     * Find takeover type by business type
     *
     * @param businessTypeList
     * @return
     */
    List<TakeoverType> findByBusinessTypeIn(List<BusinessType> businessTypeList);
}
