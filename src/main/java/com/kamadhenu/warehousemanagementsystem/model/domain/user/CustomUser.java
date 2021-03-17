package com.kamadhenu.warehousemanagementsystem.model.domain.user;

import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom user model
 */
public class CustomUser extends User implements UserDetails {

    /**
     * Create custom user
     *
     * @param user
     */
    public CustomUser(final User user) {
        super(user);
    }

    /**
     * Get granted authorities
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<String> roles = new HashSet<>();
        roles.add(super.getRole());
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    /**
     * Get password
     *
     * @return
     */
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * Get username
     *
     * @return
     */
    @Override
    public String getUsername() {
        return super.getEmployee().getName();
    }

    /**
     * Get if account is not expired
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return super.getActive();
    }

    /**
     * Get if account is not locked
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return super.getActive();
    }

    /**
     * Get if credentials are not expired
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return super.getActive();
    }

    /**
     * Get if account is enabled
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return super.getActive();
    }
}
