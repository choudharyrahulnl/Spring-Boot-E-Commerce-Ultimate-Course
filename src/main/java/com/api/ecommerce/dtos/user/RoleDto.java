package com.api.ecommerce.dtos.user;

import com.api.ecommerce.entities.user.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
@Data
public class RoleDto implements Serializable {
    private final Long id;
    private final String name;
    private final String description;

    @Override
    public String toString() {
        return name;
    }
}