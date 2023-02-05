package com.api.ecommerce.services.category;

import com.api.ecommerce.dtos.category.CategoryDto;
import com.api.ecommerce.dtos.category.CategoryListDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);
    List<CategoryListDto> findAll();

    CategoryDto findById(Long id);

    List<CategoryDto> listAllCategories();
}
