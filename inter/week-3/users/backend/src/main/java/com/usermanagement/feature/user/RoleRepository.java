package com.usermanagement.feature.user;

import com.usermanagement.feature.user.Role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Timur Berezhnoi
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(RoleType roleType);
}
