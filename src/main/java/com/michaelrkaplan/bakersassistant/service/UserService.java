package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.dto.UserRequest;

public interface UserService {
    void registerUser(UserRequest userRequest);

    boolean authenticateUser(String email, String password);

}
