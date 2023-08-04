package com.usermanagement.feature.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Timur Berezhnoi
 */
public final class UserDTO {

    @NotNull(message = "user.email.null")
    @Email(regexp = "^[a-z0-9-\\+]+(\\.[a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)$", message = "user.email.invalid")
    @Size(min = 6, max = 40, message = "user.email.length")
    private final String email;

    @NotNull(message = "user.password.null")
    @Size(min = 5, max = 20, message = "user.password.length")
    private final String password;

    @JsonCreator
    public UserDTO(@JsonProperty("email") String email,
                   @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
