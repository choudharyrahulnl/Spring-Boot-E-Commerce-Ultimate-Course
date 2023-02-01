package com.api.ecommerce.services;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.dtos.UserStatusDto;

import java.util.List;

public interface UserService {

    public UserDto save(UserDto userDto);

    public UserDto findById(Long id);

    public List<UserListDto> findAll(int page, int size);

    public UserDto update(UserDto userDto);

    public void delete(Long id);

    Boolean existsByEmail(String email);

    UserStatusDto updateStatus(Long id, Boolean status);
}
