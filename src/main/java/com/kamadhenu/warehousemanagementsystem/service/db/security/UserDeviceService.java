package com.kamadhenu.warehousemanagementsystem.service.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserDevice;

import java.util.List;
import java.util.Optional;

/**
 * UserDevice interface
 */
public interface UserDeviceService {

    /**
     * Get user device
     *
     * @param id
     * @return
     */
    Optional<UserDevice> get(Integer id);

    /**
     * Edit user device
     *
     * @param userDevice
     * @return
     */
    UserDevice edit(UserDevice userDevice);

    /**
     * Delete user device
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all user device basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<UserDevice> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all user device
     *
     * @return
     */
    List<UserDevice> getAll();

    /**
     * Get user devices by user
     *
     * @param user
     * @return
     */
    List<UserDevice> getByUser(User user);
}
