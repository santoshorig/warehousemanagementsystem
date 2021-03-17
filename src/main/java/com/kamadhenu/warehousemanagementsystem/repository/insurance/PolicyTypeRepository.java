package com.kamadhenu.warehousemanagementsystem.repository.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Policy Type repository class
 */
@Repository
public interface PolicyTypeRepository extends JpaRepository<PolicyType, Integer> {

}
