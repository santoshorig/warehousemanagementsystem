package com.kamadhenu.warehousemanagementsystem.repository.insurance;

import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Policy Owner repository class
 */
@Repository
public interface PolicyOwnerRepository extends JpaRepository<PolicyOwner, Integer> {

}
