package com.api.ecommerce.dtos.user;

import com.api.ecommerce.entities.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserListDto implements Serializable {
    private final Long id;

    @NotNull(message = "First name is required")
    private final String firstName;

    @NotNull(message = "Last name is required")
    private final String lastName;

    @NotNull(message = "Email is required")
    private final String email;

    private final String photos;

    private final boolean enabled;

}