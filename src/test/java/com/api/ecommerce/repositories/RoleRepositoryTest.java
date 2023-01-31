package com.api.ecommerce.repositories;

import com.api.ecommerce.entities.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    void testCreateRoleAdmin() {
        Role roleAdmin = Role.builder().name("Admin").description("Manage Everything").build();
        Role savedRole = roleRepository.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void testCreateRoleSalesPerson() {
        Role roleSalesPerson = Role.builder().name("SalesPerson").description("Manage Product price, customers, shipping, orders and sales report").build();
        Role savedRole = roleRepository.save(roleSalesPerson);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void testCreateRoleEditor() {
        Role roleEditor = Role.builder().name("Editor").description("Manage categories, brands, products, articles and menus").build();
        Role savedRole = roleRepository.save(roleEditor);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void testCreateRoleShipper() {
        Role roleShipper = Role.builder().name("Shipper").description("Manage categories, brands, products, articles and menus").build();
        Role savedRole = roleRepository.save(roleShipper);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    void testCreateRoleAssistant() {
        Role roleAssistant = Role.builder().name("Assistant").description("Manage questions and reviews").build();
        Role savedRole = roleRepository.save(roleAssistant);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }
}