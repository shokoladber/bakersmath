package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.repository.RoleRepository;
import org.springframework.stereotype.Service;

import com.michaelrkaplan.bakersassistant.model.Role;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void initializeRoles() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
        // Add more roles as needed
    }

    private void createRoleIfNotExists(String roleName) {
        Role existingRole = roleRepository.findByName(roleName);
        if (existingRole == null) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
        }
    }
}

