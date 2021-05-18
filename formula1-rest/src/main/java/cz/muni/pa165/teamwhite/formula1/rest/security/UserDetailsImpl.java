package cz.muni.pa165.teamwhite.formula1.rest.security;

import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author Jakub Fajkus
 */
public class UserDetailsImpl implements UserDetails {
    private final UserDTO user;


    public UserDetailsImpl(UserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(getRole()));

        return grantedAuthorities;
    }

    public String getRole() {
        if (user.isManager()) {
            return Role.ROLE_MANAGER;
        } else {
            return Role.ROLE_ENGINEER;

        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "user=" + user +
                "authorities=" + getAuthorities() +
                '}';
    }
}
