package com.api.ecommerce.mappers.category;

import com.api.ecommerce.dtos.category.CategoryListDto;
import com.api.ecommerce.entities.category.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryListMapper {
    Category toEntity(CategoryListDto categoryListDto);

    CategoryListDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryListDto categoryListDto, @MappingTarget Category category);
}