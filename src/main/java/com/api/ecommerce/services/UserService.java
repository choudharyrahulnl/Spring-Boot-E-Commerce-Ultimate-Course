package com.api.ecommerce.services;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListPaginationDto;
import com.api.ecommerce.dtos.UserStatusDto;
import org.springframework.data.domain.Sort;

public interface UserService {

    public UserDto save(UserDto userDto);

    public UserDto findById(Long id);

    public UserListPaginationDto findAll(int page, int size, Sort.Direction direction, String sortBy, String keyword);

    public UserDto update(UserDto userDto);

    public void delete(Long id);

    Boolean existsByEmail(String email);

    UserStatusDto updateStatus(Long id, Boolean status);
}
