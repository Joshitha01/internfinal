package com.usermanagement.feature.user;

import org.springframework.stereotype.Component;

/**
 * Maps user dto to user entity and
 * vice versa.
 *
 * @author Timur Berezhnoi.
 */
@Component
class UserConverter {

    /**
     * Maps the user dto to a user entity.
     *
     * @param userDTO the dto that should be mapped to entity
     * @return a user entity
     */
    User createFrom(UserDTO userDTO) {
        return new User(userDTO.getEmail(),
                        userDTO.getPassword(),
                        null);
    }

    /**
     * Maps the user entity to user dto
     *
     * @param user the entity that should be mapped to dto
     * @return a user dto
     */
    UserDTO createFrom(User user) {
        return new UserDTO(user.getEmail(), user.getPassword());
    }
}
