package com.api.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.api.ecommerce.entities.User} entity
 */
@Data
public class UserStatusDto implements Serializable {
    private final Long id;

    private final Boolean enabled;

}