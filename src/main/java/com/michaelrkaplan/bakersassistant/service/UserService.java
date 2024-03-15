package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.model.Role;
import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.RoleRepository;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String email, String password) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);
        userRepository.save(user);
    }
}
