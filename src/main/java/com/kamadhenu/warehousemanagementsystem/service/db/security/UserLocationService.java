package com.kamadhenu.warehousemanagementsystem.service.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserLocation;

import java.util.List;
import java.util.Optional;

/**
 * UserLocation interface
 */
public interface UserLocationService {

    /**
     * Get user location
     *
     * @param id
     * @return
     */
    Optional<UserLocation> get(Integer id);

    /**
     * Edit user location
     *
     * @param userLocation
     * @return
     */
    UserLocation edit(UserLocation userLocation);

    /**
     * Delete user location
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all user location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<UserLocation> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all user location
     *
     * @return
     */
    List<UserLocation> getAll();

    /**
     * Get user locations by user
     *
     * @param user
     * @return
     */
    List<UserLocation> getByUser(User user);
}
