package com.api.ecommerce.api.category;

import com.api.ecommerce.dtos.category.CategoryDto;
import com.api.ecommerce.dtos.category.CategoryListDto;
import com.api.ecommerce.services.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryApi {

    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('Editor','Admin')")
    @GetMapping
    public ResponseEntity<List<CategoryListDto>> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Editor','Admin')")
    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.save(categoryDto);
        // TODO: Save image
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Editor','Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

}
