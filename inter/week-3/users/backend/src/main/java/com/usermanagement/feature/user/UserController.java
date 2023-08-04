package com.usermanagement.feature.user;

import com.usermanagement.feature.constants.EndpointPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Timur Berezhnoi.
 */
@RestController
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping(EndpointPath.USERS)
    List<String> getUsers() {
        return userService.findAll().stream().map(userConverter::createFrom).map(UserDTO::getEmail).collect(toList());
    }

    @PostMapping(EndpointPath.USER)
    ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userConverter.createFrom(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(EndpointPath.USER + "/{email}")
    ResponseEntity<?> deleteUser(@PathVariable String email, Principal principal) {
        if(principal.getName().equals(email)) {
            throw new IllegalStateException("You can't delete your own account while being logged in.");
        }
        userService.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}
