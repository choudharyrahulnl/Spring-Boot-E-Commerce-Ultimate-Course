package com.api.ecommerce.repositories;

import com.api.ecommerce.entities.Role;
import com.api.ecommerce.entities.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Order(2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    void testCreateUser() {
        Role roleAdmin = entityManager.find(Role.class, 1L);
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.smith@gmail.com")
                .password("123456")
                .build();
        user.addRole(roleAdmin);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void testCreateUserWithTwoRoles() {
        Role roleSalesPerson = Role.builder().id(4L).build();
        Role roleEditor = Role.builder().id(5L).build();

        User user = User.builder()
                .firstName("Joe")
                .lastName("Johnson")
                .email("joe.johnson@gmail.com")
                .password("123456")
                .build();
        user.addRole(roleSalesPerson);
        user.addRole(roleEditor);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void testListAllUsers() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    @Order(4)
    void testGetUserById() {
        User user = userRepository.findById(1L).get();
        assertThat(user).isNotNull();
    }

    @Test
    @Order(5)
    void testUpdateUserDetails() {
        User user = userRepository.findById(1L).get();
        user.setLastName("Smith");
        userRepository.save(user);
    }

    @Test
    @Order(6)
    void testUpdateUserRoles() {
        User user = userRepository.findById(2L).get();
        Role roleSalesPerson = Role.builder().id(4L).build();
        user.getRoles().remove(roleSalesPerson);
        Role roleShipper = Role.builder().id(3L).build();
        user.addRole(roleShipper);
        userRepository.save(user);
    }

    @Test
    @Order(7)
    void testDeleteUser() {
        Long userId = 2L;
        userRepository.deleteById(userId);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

}