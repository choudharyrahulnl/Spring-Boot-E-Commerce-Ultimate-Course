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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    @Override
    public List<CategoryDto> listAllCategories() {
        List<Category> categories = new ArrayList<>();
        List<Category> allCategories = categoryRepository.findAll();
        for (Category category : allCategories) {
            if (category.getParent() == null) {
                categories.add(Category.builder().name(category.getName()).build());

                Set<Category> children = category.getChildren();
                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categories.add(Category.builder().name(name).build());
                    listChildren(categories, subCategory, 1);
                }
            }
        }
        return categories.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    private void listChildren(List<Category> categoryList, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();
        for (Category subCategory : children) {
            String name  = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            categoryList.add(Category.builder().name(name).build());
            listChildren(categoryList, subCategory, newSubLevel);
        }
    }
}
