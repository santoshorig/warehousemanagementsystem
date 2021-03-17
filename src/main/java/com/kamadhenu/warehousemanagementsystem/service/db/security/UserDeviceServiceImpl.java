package com.kamadhenu.warehousemanagementsystem.service.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserDevice;
import com.kamadhenu.warehousemanagementsystem.repository.security.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    /**
     * Get user device
     *
     * @param id
     * @return
     */
    @Override
    public Optional<UserDevice> get(Integer id) {
        return userDeviceRepository.findById(id);
    }

    /**
     * Edit user device
     *
     * @param userDevice
     * @return
     */
    @Override
    public UserDevice edit(UserDevice userDevice) {
        return userDeviceRepository.save(userDevice);
    }

    /**
     * Delete user device
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userDeviceRepository.deleteById(id);
    }

    /**
     * Get all user device basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<UserDevice> getAll(Integer pageNumber, Integer pageSize) {
        return userDeviceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all user device
     *
     * @return
     */
    @Override
    public List<UserDevice> getAll() {
        return userDeviceRepository.findAll();
    }

    /**
     * Get user devices by user
     *
     * @param user
     * @return
     */
    public List<UserDevice> getByUser(User user) {
        return userDeviceRepository.findByUser(user);
    }
}
