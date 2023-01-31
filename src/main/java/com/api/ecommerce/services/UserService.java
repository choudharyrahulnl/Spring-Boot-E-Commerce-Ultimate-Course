package com.api.ecommerce.services;

import com.api.ecommerce.dtos.RoleDto;
import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;

import java.util.List;

public interface UserService {

    public UserDto save(UserDto userDto);

    public UserDto findById(Long id);

    public List<UserListDto> findAll();

    public UserDto update(UserDto userDto);

    public void delete(Long id);

    Boolean existsByEmail(String email);
}
