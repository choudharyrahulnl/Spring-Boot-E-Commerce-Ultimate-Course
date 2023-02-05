package com.api.ecommerce.dtos.user;

import com.api.ecommerce.entities.user.User;
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