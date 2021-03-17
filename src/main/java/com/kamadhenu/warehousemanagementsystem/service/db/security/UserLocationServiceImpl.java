package com.kamadhenu.warehousemanagementsystem.service.db.security;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.db.security.UserLocation;
import com.kamadhenu.warehousemanagementsystem.repository.security.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLocationServiceImpl implements UserLocationService {

    @Autowired
    private UserLocationRepository userLocationRepository;

    /**
     * Get user location
     *
     * @param id
     * @return
     */
    @Override
    public Optional<UserLocation> get(Integer id) {
        return userLocationRepository.findById(id);
    }

    /**
     * Edit user location
     *
     * @param userLocation
     * @return
     */
    @Override
    public UserLocation edit(UserLocation userLocation) {
        return userLocationRepository.save(userLocation);
    }

    /**
     * Delete user location
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userLocationRepository.deleteById(id);
    }

    /**
     * Get all user location basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<UserLocation> getAll(Integer pageNumber, Integer pageSize) {
        return userLocationRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all user location
     *
     * @return
     */
    @Override
    public List<UserLocation> getAll() {
        return userLocationRepository.findAll();
    }

    /**
     * Get user locations by user
     *
     * @param user
     * @return
     */
    public List<UserLocation> getByUser(User user) {
        return userLocationRepository.findByUser(user);
    }
}
