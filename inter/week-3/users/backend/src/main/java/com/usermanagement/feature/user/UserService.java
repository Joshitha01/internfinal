package com.usermanagement.feature.user;

import com.usermanagement.feature.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.usermanagement.feature.user.Role.RoleType.ROLE_APPLICATION_ADMIN;

/**
 * @author Timur Berezhnoi
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void createUser(User user) {
        checkIfUserExist(user.getEmail());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByRoleType(ROLE_APPLICATION_ADMIN));

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    /* ---------- Helper methods ---------- */

    private void checkIfUserExist(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistException("Email is already in use!");
        });
    }
}
