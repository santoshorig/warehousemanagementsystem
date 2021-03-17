package com.kamadhenu.warehousemanagementsystem.repository.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository class
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by email
     *
     * @param email
     * @return
     */
    @Query("SELECT u from User u WHERE email = ?1")
    Optional<User> findByEmail(String email);
}
