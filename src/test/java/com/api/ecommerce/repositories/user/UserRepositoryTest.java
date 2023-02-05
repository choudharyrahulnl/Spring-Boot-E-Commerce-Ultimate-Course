package com.api.ecommerce.repositories.user;

import com.api.ecommerce.entities.user.Role;
import com.api.ecommerce.entities.user.User;
import com.api.ecommerce.repositories.user.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

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

    /**
     *     select
     *         r1_0.id,
     *         r1_0.description,
     *         r1_0.name
     *     from
     *         roles r1_0
     *     where
     *         r1_0.id=?
     *
     *
     *     insert
     *     into
     *         users
     *         (email, enabled, first_name, last_name, password, photos)
     *     values
     *         (?, ?, ?, ?, ?, ?)
     *
     *
     *     insert
     *     into
     *         user_roles
     *         (user_id, role_id)
     *     values
     *         (?, ?)
     */
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


    /**
     *     insert
     *     into
     *         users
     *         (email, enabled, first_name, last_name, password, photos)
     *     values
     *         (?, ?, ?, ?, ?, ?)
     *
     *
     *     insert
     *     into
     *         user_roles
     *         (user_id, role_id)
     *     values
     *         (?, ?) 2,4
     *
     *
     *     insert
     *     into
     *         user_roles
     *         (user_id, role_id)
     *     values
     *         (?, ?) 2,5
     */
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


    /**
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     */
    @Test
    @Order(3)
    void testListAllUsers() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }


    /**
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     *     where
     *         u1_0.id=?
     */
    @Test
    @Order(4)
    void testGetUserById() {
        User user = userRepository.findById(1L).get();
        assertThat(user).isNotNull();
    }


    /**
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     *     where
     *         u1_0.id=?
     *
     *
     *     update
     *         users
     *     set
     *         email=?,
     *         enabled=?,
     *         first_name=?,
     *         last_name=?,
     *         password=?,
     *         photos=?
     *     where
     *         id=?
     */
    @Test
    @Order(5)
    void testUpdateUserDetails() {
        User user = userRepository.findById(1L).get();
        user.setLastName("Smith");
        userRepository.save(user);
    }


    /**
     *   Find User with id 2L
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     *     where
     *         u1_0.id=? 2
     *
     *    Find user.getRoles() - LAZY FETCH
     *     select
     *         r1_0.user_id,
     *         r1_1.id,
     *         r1_1.description,
     *         r1_1.name
     *     from
     *         user_roles r1_0
     *     join
     *         roles r1_1
     *             on r1_1.id=r1_0.role_id
     *     where
     *         r1_0.user_id=? 2
     *
     *
     *     Find Role with id 3L
     *     select
     *         r1_0.id,
     *         r1_0.description,
     *         r1_0.name
     *     from
     *         roles r1_0
     *     where
     *         r1_0.id=? 3
     *
     *
     *     Delete Role with id 4L from users_roles
     *     delete
     *     from
     *         user_roles
     *     where
     *         user_id=? 2
     *         and role_id=? 4
     *
     *     Insert User with id 2L & Role with id 3L into users_roles
     *     insert
     *     into
     *         user_roles
     *         (user_id, role_id)
     *     values
     *         (?, ?) 2,3
     */
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


    /**
     *   Find User with id 2L
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     *     where
     *         u1_0.id=? 2
     *
     *
     *     Delete User with id 2L from users_roles
     *     First Delete Mapping from users_roles table
     *     Then Delete User from users table
     *     delete
     *     from
     *         user_roles
     *     where
     *         user_id=? 2
     *
     *
     *    Delete User with id 2L
     *    delete
     *     from
     *         users
     *     where
     *         id=? 2
     *
     *
     *    Find all users
     *    select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     */
    @Test
    @Order(7)
    void testDeleteUser() {
        Long userId = 2L;
        userRepository.deleteById(userId);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }


    /**
     *   Insert User into users table without role
     *   This will only save the user details
     *   Table user_roles will not be updated
     *   insert
     *     into
     *         users
     *         (email, enabled, first_name, last_name, password, photos)
     *     values
     *         (?, ?, ?, ?, ?, ?)
     *
     */
    @Test
    @Order(8)
    void testCreateUserWithoutRole() {
        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test.user@gmail.com")
                .password("654321")
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(9)
    void testExistsByEmail() {
        String email = "john.doe@gmail.com";
        Optional<User> byEmail = userRepository.findByEmail(email);
        assertThat(byEmail.isPresent()).isTrue();
    }

    @Test
    @Order(10)
    void testExistsByEmailFalse() {
        String email = "john.john@gmail.com";
        Optional<User> byEmail = userRepository.findByEmail(email);
        assertThat(byEmail.isPresent()).isFalse();
    }


    /**
     *   select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0 limit ?,? 0,10
     */
    @Test
    @Order(11)
    void testUsersPagination() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> users = page.getContent();

        assertThat(users.size()).isEqualTo(pageSize);
    }


    /**
     *  select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.enabled,
     *         u1_0.first_name,
     *         u1_0.last_name,
     *         u1_0.password,
     *         u1_0.photos
     *     from
     *         users u1_0
     *     where
     *         u1_0.first_name like ?
     *         or u1_0.last_name like ? limit ?,?
     */
    @Test
    @Order(12)
    void testKeywordSearch() {
        String keyword = "ana maria";
        Page<User> users = userRepository.findAll(keyword, PageRequest.of(0, 10));

        assertThat(users.getContent().size()).isGreaterThan(0);
    }

}