package com.api.ecommerce.services.impl;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.dtos.UserListPaginationDto;
import com.api.ecommerce.dtos.UserStatusDto;
import com.api.ecommerce.entities.Role;
import com.api.ecommerce.entities.User;
import com.api.ecommerce.exceptions.UserNotFoundException;
import com.api.ecommerce.mappers.RoleMapper;
import com.api.ecommerce.mappers.UserListMapper;
import com.api.ecommerce.mappers.UserListPaginationMapper;
import com.api.ecommerce.mappers.UserMapper;
import com.api.ecommerce.repositories.UserRepository;
import com.api.ecommerce.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserListMapper userListMapper;
    private final UserListPaginationMapper userListPaginationMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper, UserListMapper userListMapper, UserListPaginationMapper userListPaginationMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userListMapper = userListMapper;
        this.userListPaginationMapper = userListPaginationMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto userDto) {

        User user = userMapper.toEntity(userDto);

        // Check if email already exists
        Boolean existsByEmail = existsByEmail(user.getEmail());
        if (existsByEmail) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Add role to user
        if (userDto.getRoles().size() != 0) {
            userDto.getRoles().forEach(role -> user.addRole(roleMapper.toEntity(role)));
        } else {
            user.addRole(Role.builder().id(2L).build());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserListPaginationDto findAll(int page, int size, Sort.Direction direction, String sortBy) {

        // Sorting
        Sort sort = Sort.by(direction, sortBy);

        // Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get users
        Page<User> pages = userRepository.findAll(pageable);

        long totalItems = pages.getTotalElements();
        int totalPages = pages.getTotalPages();
        int itemsPerPage = pages.getNumberOfElements();
        int currentPage = pages.getNumber();

        List<User> users = pages.getContent();

        List<UserListDto> userListDtos = users.stream().map(user -> userListMapper.toDto(user)).collect(Collectors.toList());

        // Return users
        UserListPaginationDto userListPaginationDto = userListPaginationMapper.toDto(currentPage, totalItems, totalPages, itemsPerPage, userListDtos);
        return userListPaginationDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        // Find User
        Optional<User> byId = Optional.of(userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found")));

        // If user is not null, update user
        if (byId.isPresent()) {

            User user = byId.get();

            // Update user roles
            user.getRoles().clear();
            userDto.getRoles().forEach(roleDto -> user.addRole(roleMapper.toEntity(roleDto)));

            // Update user password
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            // Update user
            userMapper.partialUpdate(userDto, user);
        }

        return userMapper.toDto(byId.get());
    }

    @Override
    public void delete(Long id) {
        // Find User
        UserDto byId = findById(id);

        // If user is not null, delete user
        if (byId != null) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    @Override
    public UserStatusDto updateStatus(Long id, Boolean status) {
        // Find User
        Optional<User> byId = Optional.of(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));

        // If user is not null, update user status
        if (byId.isPresent()) {
            User user = byId.get();
            user.setEnabled(status);
        }

        return new UserStatusDto(id, status);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }


}
