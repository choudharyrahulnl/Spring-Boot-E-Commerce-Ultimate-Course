package com.api.ecommerce.dtos;

import com.api.ecommerce.entities.users.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {
    private final Long id;

    @NotNull(message = "First name is required")
    private final String firstName;

    @NotNull(message = "Last name is required")
    private final String lastName;

    @NotNull(message = "Email is required")
    private final String email;

    private final String password;

    private String photos;

    private final boolean enabled = true;

    private final Set<RoleDto> roles;

}