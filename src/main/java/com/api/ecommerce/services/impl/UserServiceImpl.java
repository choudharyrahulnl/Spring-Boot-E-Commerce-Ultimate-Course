package com.api.ecommerce.services.impl;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.entities.Role;
import com.api.ecommerce.entities.User;
import com.api.ecommerce.mappers.RoleMapper;
import com.api.ecommerce.mappers.UserListMapper;
import com.api.ecommerce.mappers.UserMapper;
import com.api.ecommerce.repositories.UserRepository;
import com.api.ecommerce.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserListMapper userListMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper, UserListMapper userListMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userListMapper = userListMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto userDto) {

        User user = userMapper.toEntity(userDto);

        passwordEncoder.encode(user.getPassword());

        // Add role to user
        if(userDto.getRoles().size() != 0){
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
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserListDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userListMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) {
        // Find User
        Optional<User> byId = Optional.of(userRepository.findById(userDto.getId())
                .orElseThrow(EntityNotFoundException::new));

        // If user is not null, update user
        if (byId.isPresent()) {

            User user = byId.get();

            // Update user roles
            user.getRoles().clear();
            userDto.getRoles().forEach(roleDto -> user.addRole(roleMapper.toEntity(roleDto)));

            // Update user password
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

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
        if (byId != null){
            userRepository.deleteById(id);
        }
    }

}
