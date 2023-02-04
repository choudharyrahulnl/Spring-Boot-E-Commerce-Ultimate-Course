package com.api.ecommerce.dtos;

import com.api.ecommerce.entities.users.User;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserStatusDto implements Serializable {
    private final Long id;

    private final boolean enabled;

}