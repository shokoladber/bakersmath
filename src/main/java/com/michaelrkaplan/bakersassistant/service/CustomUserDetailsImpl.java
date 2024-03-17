package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImpl implements CustomUserDetails {

    private User user;

    // constructor is used for autowire by constructor
    public CustomUserDetailsImpl(User user) {
        this.user = user;
    }

    // default constructor to avoid BeanInstantiationException for autowire
    public CustomUserDetailsImpl(){
        // Default constructor
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
