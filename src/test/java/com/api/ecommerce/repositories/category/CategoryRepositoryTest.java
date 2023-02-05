package com.api.ecommerce.repositories.category;

import com.api.ecommerce.entities.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Order(2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    void testCreateCategory() {
        var category = new Category();
        category.setName("Electronics");
        category.setAlias("Electronics items");
        category.setImage("https://www.google.com");
        category.setEnabled(true);

        var savedCategory = categoryRepository.save(category);

        assertTrue(savedCategory.getId() > 0);
    }

    @Test
    @Order(2)
    void testFindAll() {
        var categories = categoryRepository.findAll();
        assertTrue(categories.size() > 0);
    }

    @Test
    @Order(3)
    void testCreateSubCategory() {
        Category parent = Category.builder().id(1L).build();
        Category subCategory = Category.builder().name("Camera").alias("Camera items").image("https://www.google.com").enabled(true).parent(parent).build();
        Category savedCategory = categoryRepository.save(subCategory);
        assertTrue(savedCategory.getId() > 0);
    }

    @Test
    @Order(4)
    void testCreateSubSubCategory() {
        Category parent = Category.builder().id(2L).build();
        Category subCategory = Category.builder().name("DSLR Cameras").alias("DSLR Camera items").image("https://www.google.com").enabled(true).parent(parent).build();
        Category savedCategory = categoryRepository.save(subCategory);
        assertTrue(savedCategory.getId() > 0);
    }

    @Test
    @Order(5)
    void testGetCategory() {
        Category category = categoryRepository.findById(1L).get();
        log.info("Category: " + category.getName());

        Set<Category> subCategories = category.getChildren();
        for (Category subCategory : subCategories) {
            log.info("Sub Category: " + subCategory.getName());
        }

        assertTrue(subCategories.size() > 0);
    }

    @Test
    @Order(6)
    void testGetSubCategory() {
        // Find All Categories
        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            // If the category has no parent, it is a root category
            if (category.getParent() == null) {
                log.info(category.getName());

                // Get the children of the root category
                Set<Category> children = category.getChildren();

                // Print the children
                for (Category subCategory : children) {
                    log.info("--" + subCategory.getName());

                    // Print the sub children in a recursive manner
                    printChildren(subCategory, 1);
                }
            }
        }

    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            for (int i = 0; i < newSubLevel; i++) {
                log.info("--");
            }

            log.info(subCategory.getName());

            printChildren(subCategory, newSubLevel);
        }
    }


}