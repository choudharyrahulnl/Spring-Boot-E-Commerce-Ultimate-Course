package com.api.ecommerce.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.api.ecommerce.entities.Role} entity
 */
@Data
public class RoleDto implements Serializable {
    private final Long id;
    private final String name;
    private final String description;
}