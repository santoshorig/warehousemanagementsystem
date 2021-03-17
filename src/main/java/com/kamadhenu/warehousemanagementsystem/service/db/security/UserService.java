package com.kamadhenu.warehousemanagementsystem.service.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;

import java.util.List;
import java.util.Optional;

/**
 * User interface
 */
public interface UserService {

    /**
     * Get user
     *
     * @param id
     * @return
     */
    Optional<User> get(Integer id);

    /**
     * Edit user
     *
     * @param user
     * @return
     */
    User edit(User user);

    /**
     * Delete user
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all users basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<User> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all users
     *
     * @return
     */
    List<User> getAll();
}
