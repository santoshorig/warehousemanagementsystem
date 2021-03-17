package com.kamadhenu.warehousemanagementsystem.service.domain.user;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.domain.user.CustomUser;
import com.kamadhenu.warehousemanagementsystem.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom user service
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user by username / email
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public CustomUser loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User userModel = user.get();
            CustomUser customUser = user.map(CustomUser::new).get();
            customUser.setEmployee(userModel.getEmployee());
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

}
