package com.michaelrkaplan.bakersmath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michaelrkaplan.bakersmath.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
