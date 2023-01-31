package com.api.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.api.ecommerce.entities.User} entity
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

    private final Boolean enabled;

}