package com.michaelrkaplan.bakersassistant.model;

import com.michaelrkaplan.bakersassistant.model.CustomUserDetails;
import com.michaelrkaplan.bakersassistant.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsImpl implements CustomUserDetails {

    private User user;

    // default constructor to avoid BeanInstantiationException for autowire
    public CustomUserDetailsImpl(){
        // Default constructor
    }

    public CustomUserDetailsImpl(User user) {
        this.user = user;
    }

    public List<Recipe> getRecipes() {return user.getRecipes();}

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


}
