package com.api.ecommerce.dtos.category;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.api.ecommerce.entities.category.Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private final Long id;
    private final String name;
    private final String alias;
    private final String image;
    private final Boolean enabled;
    private final CategoryDto parent;
    private final Set<CategoryDto> children;

}