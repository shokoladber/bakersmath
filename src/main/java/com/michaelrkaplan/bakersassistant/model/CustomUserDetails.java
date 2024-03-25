package com.michaelrkaplan.bakersassistant.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public interface CustomUserDetails extends UserDetails {

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the roles/authorities of the user
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Implement getPassword, getUsername, getEmail, isAccountNonExpired,
    // isAccountNonLocked, isCredentialsNonExpired, isEnabled as abstract methods
    // These methods must be implemented by the classes that implement this interface
    String getPassword();

    String getUsername();

    String getEmail();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();

}
