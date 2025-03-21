package com.michaelrkaplan.bakersmath.repository;

import com.michaelrkaplan.bakersmath.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findById(Long id);
}
