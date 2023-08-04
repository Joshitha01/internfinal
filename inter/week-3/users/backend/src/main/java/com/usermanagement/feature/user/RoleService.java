package com.usermanagement.feature.user;

import com.usermanagement.feature.exception.RoleNotFoundException;
import com.usermanagement.feature.user.Role.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Timur Berezhnoi
 */
@Service
@Transactional
class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Role findByRoleType(final RoleType roleType) {
        return roleRepository.findByRoleType(roleType).orElseThrow(() -> new RoleNotFoundException("Role: " + roleType + " not found."));
    }
}
