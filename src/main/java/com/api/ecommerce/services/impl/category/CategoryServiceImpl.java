package com.api.ecommerce.services.impl.category;

import com.api.ecommerce.dtos.category.CategoryDto;
import com.api.ecommerce.dtos.category.CategoryListDto;
import com.api.ecommerce.entities.category.Category;
import com.api.ecommerce.exceptions.CategoryNotFoundException;
import com.api.ecommerce.mappers.category.CategoryListMapper;
import com.api.ecommerce.mappers.category.CategoryMapper;
import com.api.ecommerce.repositories.category.CategoryRepository;
import com.api.ecommerce.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryListMapper categoryListMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryListMapper categoryListMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryListMapper = categoryListMapper;
    }

    @Override
    public List<CategoryListDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryListMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return categoryMapper.toDto(category);
    }
}
