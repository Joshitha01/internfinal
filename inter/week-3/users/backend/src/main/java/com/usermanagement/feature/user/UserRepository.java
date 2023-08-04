package com.usermanagement.feature.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Timur Berezhnoi
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
